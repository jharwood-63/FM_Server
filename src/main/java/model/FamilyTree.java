package model;

import dao.DataAccessException;
import dao.eventDAO;
import dao.personDAO;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

public class FamilyTree {
    private final static String FEMALE = "f";
    private final static String MALE ="m";

    private final static String BIRTH_EVENT = "birth";
    private final static String MARRIAGE_EVENT = "marriage";
    private final static String DEATH_EVENT = "death";
    private final static String[] OTHER_EVENTS = new String[]{"baptism", "graduation", "retirement", "first_kiss"};

    private static Data data = new Data();

    public Person generatePerson(String gender, String associateUsername, int generations, int birthYear, Connection conn) throws DataAccessException {
        Random rand = new Random();
        eventDAO eventDAO = new eventDAO(conn);
        personDAO personDAO = new personDAO(conn);
        Person mother = null;
        Person father = null;
        Person person;

        if (generations > 0) {
            int motherBirthYear = rand.nextInt((birthYear - 20) - (birthYear - 40)) + (birthYear - 40);
            int fatherBirthYear = rand.nextInt((birthYear - 20) - (birthYear - 40)) + (birthYear - 40);

            mother = generatePerson(FEMALE, associateUsername, generations - 1, motherBirthYear, conn);
            father = generatePerson(MALE, associateUsername, generations - 1, fatherBirthYear, conn);

            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            int youngest = findYoungest(motherBirthYear, fatherBirthYear);
            int marriageYear = rand.nextInt((youngest + 50) - (youngest + 13)) + (youngest + 13);

            Event motherMarriage = createEvent(MARRIAGE_EVENT, motherBirthYear, null, marriageYear);
            Location marriageLocation = new Location (motherMarriage.getCountry(), motherMarriage.getCity(), String.valueOf(motherMarriage.getLatitude()),
                    String.valueOf(motherMarriage.getLongitude()));

            Event fatherMarriage = createEvent(MARRIAGE_EVENT, fatherBirthYear, marriageLocation, motherMarriage.getYear());

            setEvents(motherMarriage, associateUsername, mother.getPersonID());
            setEvents(fatherMarriage, associateUsername, father.getPersonID());

            try {
                eventDAO.insertEvent(motherMarriage);
                eventDAO.insertEvent(fatherMarriage);

                personDAO.insertPerson(mother);
                personDAO.insertPerson(father);
            }
            catch (DataAccessException e) {
                e.printStackTrace();
                throw new DataAccessException("Error inserting events and persons");
            }

        }

        if (father == null && mother == null) {
            person = createPerson(associateUsername, gender, null, null, null);
        }
        else {
            person = createPerson(associateUsername, gender, father.getPersonID(), mother.getPersonID(), null);
        }

        Event birth = createEvent(BIRTH_EVENT, birthYear, null, 0);
        Event death = createEvent(DEATH_EVENT, birthYear, null, 0);
        Event random = createEvent(OTHER_EVENTS[rand.nextInt(OTHER_EVENTS.length)], birthYear, null, 0);

        setEvents(birth, associateUsername, person.getPersonID());
        setEvents(death, associateUsername, person.getPersonID());
        setEvents(random, associateUsername, person.getPersonID());

        try {
            eventDAO.insertEvent(birth);
            eventDAO.insertEvent(death);
            eventDAO.insertEvent(random);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error inserting events");
        }

        return person;
    }

    private void setEvents(Event event, String associateUsername, String personID) {
        event.setAssociatedUsername(associateUsername);
        event.setPersonID(personID);
    }

    /*
    * YEARS
    * birth of parents will be between 20 and 40 years before child
    * marriage of parents will be between 1 and 20 years before birth of user
    * max death year is 100 years after birth, must be after birth of child
     */

    private Event createEvent(String eventType, int birthYear, Location marriageLocation, int marriageYear) {
        Random randIndex = new Random();
        Location location = data.getLocationData().getData()[randIndex.nextInt(data.getLocationDataLength())];
        String eventID = UUID.randomUUID().toString();
        float latitude = Float.parseFloat(location.getLatitude());
        float longitude = Float.parseFloat(location.getLongitude());
        Event event;

        /*
        * EVENT TYPES
        * Required:
        * birth
        * marriage
        * death
        * other
         */

        if (eventType.equalsIgnoreCase(BIRTH_EVENT)) {
            event = new Event(eventID, "", "", latitude, longitude, location.getCountry(),
                    location.getCity(), eventType, birthYear);
        }
        else if (eventType.equalsIgnoreCase(MARRIAGE_EVENT)) {
            if (marriageLocation == null) {
                event = new Event(eventID, "", "", latitude, longitude, location.getCountry(), location.getCity(),
                        eventType, marriageYear);
            }
            else {
                latitude = Float.parseFloat(marriageLocation.getLatitude());
                longitude = Float.parseFloat(marriageLocation.getLongitude());
                event = new Event(eventID, "", "", latitude, longitude, marriageLocation.getCountry(), marriageLocation.getCity(),
                        eventType, marriageYear);
            }
        }
        else if (eventType.equalsIgnoreCase(DEATH_EVENT)) {
            int deathYear = randIndex.nextInt((birthYear + 100) - (birthYear + 50)) + (birthYear + 50);
            event = new Event(eventID, "", "", latitude, longitude, location.getCountry(), location.getCity(),
                    eventType, deathYear);
        }
        else {
            int eventYear = randIndex.nextInt((birthYear + 100) - (birthYear + 8)) + (birthYear + 8);
            event = new Event(eventID, "", "", latitude, longitude, location.getCountry(), location.getCity(),
                    eventType, eventYear);
        }

        return event;
    }

    private Person createPerson(String associatedUsername, String gender, String fatherID, String motherID, String spouseID) {
        String personID = UUID.randomUUID().toString();
        String firstName = getFirstName(gender);
        String lastName = getLastName();

        Person person = new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);

        return person;
    }

    private String getFirstName(String gender) {
        Random rand = new Random();
        String firstName;

        if (gender.equals(FEMALE)) {
            firstName = data.getfNames().getData()[rand.nextInt(data.getFNameLength())];
        }
        else {
            firstName = data.getmNames().getData()[rand.nextInt(data.getMNameLength())];
        }

        return firstName;
    }

    private String getLastName() {
        Random rand = new Random();
        String lastName = data.getsNames().getData()[rand.nextInt(data.getSNameLength())];

        return lastName;
    }

    private int findYoungest(int motherBirthYear, int fatherBirthYear) {
        if (motherBirthYear >= fatherBirthYear) {
            return motherBirthYear;
        }
        else {
            return fatherBirthYear;
        }
    }
}

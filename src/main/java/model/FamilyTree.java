package model;

import java.util.UUID;

public class FamilyTree {
    private final static String FEMALE = "f";
    private final static String MALE ="m";

    public Person generatePerson(String gender, String associateUsername, int generations) {
        Person mother;
        Person father;
        String personID;

        if (generations > 1) {
            mother = generatePerson(FEMALE, associateUsername, generations - 1);
            father = generatePerson(MALE, associateUsername, generations - 1);

            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            //Add marriage events to mother and father
            //marriage events must be the same
        }

        personID = UUID.randomUUID().toString();
        Person person = new Person();
        //Set person's properties
        //first and last name generated by using json files
        //generate events for person except marriage

        // FIXME: I dont know where to add the user to the database3

        return person;
    }
}

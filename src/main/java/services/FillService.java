package services;

import dao.*;
import model.Event;
import model.FamilyTree;
import model.Person;
import model.User;
import services.requests.FillRequest;
import services.result.FillResult;
import services.result.Result;

import java.sql.Connection;
import java.util.Random;

/**
 * FillService receives a FillRequest from the handler and returns a FillResponse
 */

public class FillService {
    private final static int LOW_BIRTH_YEAR = 1994;
    private final static int HIGH_BIRTH_YEAR = 2006;

    /**
     * Populates the server's database with generated data for the specified username.
     * The required "username" parameter must be a user already registered with the server.
     * If there is any data in the database already associated with the given username, it is deleted.
     * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated,
     * and must be a non-negative integer
     * @param fillRequest FillRequest object received from the handler.
     * @return FillResponse object
     */

    public Result fill(FillRequest fillRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            if (fillRequest.getNumGenerations() >= 0) {
                userDAO userDAO = new userDAO(conn);
                User user = userDAO.find(fillRequest.getUsername());

                if (user != null) {
                    clearData(conn, user.getUsername());
                    int generations = fillRequest.getNumGenerations();
                    generatePersons(generations, user, conn);

                    int numPeople = calcNumPeople(generations);
                    int numEvents = calcNumEvents(numPeople);

                    manager.closeConnection(true);
                    return new FillResult(numPeople, numEvents, true);
                } else {
                    manager.closeConnection(false);
                    return new Result("Error: user does not exist", false);
                }
            }
            else {
                manager.closeConnection(false);
                return new Result("Error: invalid number of generations", false);
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Result("Error: unable to process fill request", false);
        }
    }

    private void clearData(Connection conn, String username) throws DataAccessException {
        personDAO personDAO = new personDAO(conn);
        eventDAO eventDAO = new eventDAO(conn);

        personDAO.clearPerson(username);
        eventDAO.clearEvent(username);
    }

    private void generatePersons(int generations, User user, Connection conn) throws DataAccessException {
        FamilyTree familyTree = new FamilyTree();
        Random rand = new Random();
        eventDAO eventDAO = new eventDAO(conn);
        personDAO personDAO = new personDAO(conn);

        int birthYear = rand.nextInt(HIGH_BIRTH_YEAR - LOW_BIRTH_YEAR) + LOW_BIRTH_YEAR;

        Person person = familyTree.generatePerson(user.getGender(), user.getUsername(), generations, birthYear, conn);
        Event deathEvent = eventDAO.findEvent(person.getPersonID(), "death");
        eventDAO.deleteEvent(deathEvent.getEventID());
        resetPerson(person, user);
        personDAO.insertPerson(person);
    }

    private int calcNumPeople(int generations) {
        int numAncestors = 0;
        for (int i = 1; i <= generations; i++) {
            numAncestors = (int) (numAncestors + (Math.pow(2, i)));
        }
        numAncestors++;

        return numAncestors;
    }

    private int calcNumEvents(int numPeople) {
        return (numPeople * 4) - 2;
    }

    private void resetPerson(Person person, User user) {
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
    }
}

package services;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import services.requests.LoadRequest;
import services.result.LoadResult;
import services.result.Result;

import java.sql.Connection;

/**
 * LoadService receives a request from the handler and loads the data into the database.
 */

public class LoadService {
    /**
     * Loads the user, person, and event data from the LoadRequest into the database
     * Clears all previous data from the database
     * @param loadRequest Object containing the data that will be loaded
     * @return LoadResponse object
     */

    public Result load(LoadRequest loadRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            if (hasAllValues(loadRequest)) {
                Connection conn = manager.getConnection();
                Utility utility = new Utility();

                utility.clear(conn);

                loadUsers(loadRequest.getUsers(), conn);
                loadPersons(loadRequest.getPersons(), conn);
                loadEvents(loadRequest.getEvents(), conn);

                manager.closeConnection(true);
                return new LoadResult(loadRequest.getUsers().length, loadRequest.getPersons().length,
                        loadRequest.getEvents().length, true);
            }
            else {
                manager.closeConnection(false);
                return new Result("Error: invalid request, load not completed", false);
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Result("Error: Load not completed", false);
        }
    }

    private void loadUsers(User [] users, Connection conn) throws DataAccessException {
        userDAO userDAO = new userDAO(conn);

        for (int i = 0; i < users.length; i++) {
            userDAO.insertUser(users[i]);
        }
    }

    private void loadPersons(Person[] persons, Connection conn) throws DataAccessException {
        personDAO personDAO = new personDAO(conn);

        for (int i = 0; i < persons.length; i++) {
            personDAO.insertPerson(persons[i]);
        }
    }

    private void loadEvents(Event[] events, Connection conn) throws DataAccessException {
        eventDAO eventDAO = new eventDAO(conn);

        for (int i = 0; i < events.length; i++) {
            eventDAO.insertEvent(events[i]);
        }
    }

    private boolean hasAllValues(LoadRequest loadRequest) {
        User[] users = loadRequest.getUsers();
        Person[] persons = loadRequest.getPersons();
        Event[] events = loadRequest.getEvents();

        if (users.length == 0) {
            return false;
        }
        else if (persons.length == 0) {
            return false;
        }
        else if (events.length == 0) {
            return false;
        }

        return true;
    }
}

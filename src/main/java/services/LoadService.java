package services;

import dao.DataAccessException;
import dao.DatabaseManager;
import model.Event;
import model.Person;
import model.User;
import services.requests.LoadRequest;
import services.response.LoadResponse;
import services.response.Response;

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

    public Response load(LoadRequest loadRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            Utility utility = new Utility();

            //clear everything from the database
            utility.clear(conn);
            //run through the request object and add everything to the database

            //create and return response object
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Response("Error: Load not completed", false);
        }

        return null;
    }

    private void loadUsers(User [] users) {

    }

    private void loadPersons(Person[] persons) {

    }

    private void loadEvents(Event[] events) {

    }
}

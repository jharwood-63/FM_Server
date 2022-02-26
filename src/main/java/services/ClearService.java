package services;

import dao.*;
import services.response.ClearResponse;
import services.response.Response;

import java.sql.Connection;

/**
 * Clears all data from all tables in the database
 */

public class ClearService {
    /**
     * Deletes all data from the database
     * @return ClearResponse object
     */

    public Response clear() {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);
            personDAO personDAO = new personDAO(conn);
            eventDAO eventDAO = new eventDAO(conn);
            authTokenDAO authTokenDAO = new authTokenDAO(conn);

            userDAO.clearUser();
            personDAO.clearPerson();
            eventDAO.clearEvent();
            authTokenDAO.clearAuthToken();

            manager.closeConnection(true);
            return new ClearResponse("Clear succeeded.", true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Response("Error: Unable to clear the database", false);
        }
    }
}

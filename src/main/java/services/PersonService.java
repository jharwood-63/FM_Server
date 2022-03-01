package services;

import dao.DataAccessException;
import dao.DatabaseManager;
import services.requests.PersonRequest;
import services.response.PersonResponse;

/**
 * Receives a PersonRequest from the handler and returns a PersonResponse
 */

public class PersonService {
    /**
     * Returns the single Person object with the specified ID (if the person is associated with the current user).
     * The current user is determined by the provided authtoken. OR
     * Returns ALL family members of the current user. The current user is determined by the provided authtoken.
     * @param personRequest Request object with all data needed for the operation
     * @return PersonResponse object
     */

    public PersonResponse person(PersonRequest personRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {

        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);

        }
        return null;
    }
}

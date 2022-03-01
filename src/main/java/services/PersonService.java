package services;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.authTokenDAO;
import dao.personDAO;
import model.AuthToken;
import model.Person;
import services.requests.PersonRequest;
import services.response.PersonResponse;
import services.response.Response;

import java.sql.Connection;
import java.util.Set;

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

    public Response person(PersonRequest personRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            personDAO personDAO = new personDAO(conn);
            authTokenDAO authTokenDAO = new authTokenDAO(conn);
            Utility utility = new Utility();

            AuthToken authToken = authTokenDAO.find(personRequest.getAuthtoken());
            if (authToken != null) {
                String authTokenUsername = authToken.getUsername();
                String personID = personRequest.getPersonID();

                if (personID != null) {
                    Person person = personDAO.find(personID);

                    if (person != null) {
                        if (utility.isAssociated(person.getAssociatedUsername(), authTokenUsername)) {
                            manager.closeConnection(true);
                            return new PersonResponse(person, true);
                        }
                        else {
                            manager.closeConnection(false);
                            return new Response("Person requested is not associated with the requesting user", false);
                        }
                    }
                    else {
                        manager.closeConnection(false);
                        return new Response("Unable to find specified person in database", false);
                    }
                }
                else {
                    Set<Person> persons = personDAO.findAll(authTokenUsername);
                    Person[] personArray = new Person[persons.size()];
                    persons.toArray(personArray);

                    manager.closeConnection(true);
                    return new PersonResponse(personArray, true);
                }
            }
            else {
                manager.closeConnection(false);
                return new Response("Invalid authtoken provided", false);
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Response("Error: unable to complete request", false);
        }
    }
}

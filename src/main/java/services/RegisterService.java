package services;

import dao.*;
import model.AuthToken;
import model.FamilyTree;
import model.Person;
import model.User;
import services.requests.RegisterRequest;
import services.response.RegisterResponse;
import services.response.Response;

import java.sql.Connection;
import java.util.UUID;

/**
 * RegisterService receives a RegisterRequest from the handler
 * Returns a response containing the authtoken of the new user
 */

public class RegisterService extends Service {
    /**
     * Creates a new user account
     * Generates 4 generations of ancestor data for the new user
     * Logs in the user
     * @param registerRequest Object containing the new user data
     * @return Response, an object containing the authtoken
     */

    public Response register(RegisterRequest registerRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);
            personDAO personDAO = new personDAO(conn);
            authTokenDAO authTokenDAO = new authTokenDAO(conn);
            FamilyTree familyTree = new FamilyTree();

            User user = createUser(registerRequest);
            userDAO.insertUser(user);
            //FIXME: create 4 generations with events, probably needs to send the data object
            Person person = familyTree.generatePerson(user.getGender(), user.getUsername(), 4);
            resetPerson(person, user);
            //FIXME: I dont know where to add the user to the database -> personDAO.insertPerson(person);

            // Login user
            String authTokenString = UUID.randomUUID().toString();
            AuthToken authToken = new AuthToken(authTokenString, user.getUsername());
            authTokenDAO.insertAuthToken(authToken);

            //close the connection after dao operations are done
            manager.closeConnection(true);

            return new RegisterResponse(authTokenString, user.getUsername(), user.getPersonID(), true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Response("Error: User was not registered", false);
        }
    }

    private User createUser(RegisterRequest registerRequest) {
        String personID = UUID.randomUUID().toString();

        User regUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(),
                registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getGender(), personID);

        return  regUser;
    }

    private void resetPerson(Person person, User user) {
        person.setPersonID(user.getPersonID());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
    }
}

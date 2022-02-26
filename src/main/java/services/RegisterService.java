package services;

import dao.*;
import model.*;
import services.requests.RegisterRequest;
import services.response.RegisterResponse;
import services.response.Response;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

/**
 * RegisterService receives a RegisterRequest from the handler
 * Returns a response containing the authtoken of the new user
 */

public class RegisterService {
    private final static int LOW_BIRTH_YEAR = 1994;
    private final static int HIGH_BIRTH_YEAR = 2006;

    public RegisterService() {

    }

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
            eventDAO eventDAO = new eventDAO(conn);
            FamilyTree familyTree = new FamilyTree();
            Random rand = new Random();

            //Create user
            if (userDAO.find(registerRequest.getUsername()) != null) {
                User user = createUser(registerRequest);

                //Create person
                int birthYear = rand.nextInt(HIGH_BIRTH_YEAR - LOW_BIRTH_YEAR) + LOW_BIRTH_YEAR;
                Person person = familyTree.generatePerson(user.getGender(), user.getUsername(), 4, birthYear, personDAO, eventDAO);
                Event deathEvent = eventDAO.findEvent(person.getPersonID(), "death");
                eventDAO.deleteEvent(deathEvent.getEventID());
                resetPerson(person, user);
                personDAO.insertPerson(person);

                //sync user with person and insert person
                user.setPersonID(person.getPersonID());
                userDAO.insertUser(user);

                // Login user
                String authTokenString = UUID.randomUUID().toString();
                AuthToken authToken = new AuthToken(authTokenString, user.getUsername());
                authTokenDAO.insertAuthToken(authToken);

                //close the connection after dao operations are done
                manager.closeConnection(true);

                return new RegisterResponse(authTokenString, user.getUsername(), user.getPersonID(), true);
            }
            else {
                manager.closeConnection(false);
                return new Response("Error: username already in use", false);
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Response("Error: User was not registered", false);
        }
    }

    private User createUser(RegisterRequest registerRequest) {
        User regUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(),
                registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getGender(), "");

        return  regUser;
    }

    private void resetPerson(Person person, User user) {
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
    }
}

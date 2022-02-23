package services;

import dao.*;
import model.User;
import services.requests.RegisterRequest;
import services.response.RegisterResponse;

import java.sql.Connection;
import java.util.UUID;

/**
 * RegisterService receives a RegisterRequest from the handler
 * Returns a response containing the authtoken of the new user
 */

public class RegisterService {
    /**
     * Creates a new user account
     * Generates 4 generations of ancestor data for the new user
     * Logs in the user
     * @param registerRequest Object containing the new user data
     * @return RegisterResponse, an object containing the authtoken
     */

    public RegisterResponse register(RegisterRequest registerRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);
            personDAO personDAO = new personDAO(conn);
            eventDAO eventDAO = new eventDAO(conn);

            userDAO.insertUser(createUser(registerRequest));
            //create 4 generations
            //change the personID of the person returned to the personID of the user

            //close the connection after dao operations are done
            manager.closeConnection(true);

            //create the response object
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            //return a response object with failure message
        }
        return null;
    }

    private User createUser(RegisterRequest registerRequest) {
        String personID = UUID.randomUUID().toString();

        User regUser = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(),
                registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getGender(), personID);

        return  regUser;
    }
}

package services;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.authTokenDAO;
import dao.userDAO;
import model.AuthToken;
import model.User;
import services.requests.LoginRequest;
import services.requests.RegisterRequest;
import services.response.LoginResponse;
import services.response.Response;

import java.sql.Connection;
import java.util.UUID;

/**
 * LoginService receives a LoginRequest from the handler
 * Returns a response containing the authtoken of the new user
 */

public class LoginService {
    /**
     * Logs the user in
     * @param loginRequest Request object received from the handler
     * @return LoginResponse object
     */

    public Response login(LoginRequest loginRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);
            authTokenDAO authTokenDAO = new authTokenDAO(conn);
            if (hasAllValues(loginRequest)) {
                User user = userDAO.find(loginRequest.getUsername());
                if (user != null) {
                    if (loginRequest.getPassword().equals(user.getPassword())) {
                        String authtoken = UUID.randomUUID().toString();
                        AuthToken authToken = new AuthToken(authtoken, user.getUsername());
                        authTokenDAO.insertAuthToken(authToken);

                        manager.closeConnection(true);
                        return new LoginResponse(authtoken, authToken.getUsername(), user.getPersonID(), true);
                    } else {
                        manager.closeConnection(false);
                        return new Response("Error: username or password is incorrect", false);
                    }
                } else {
                    manager.closeConnection(false);
                    return new Response("Error: username or password is incorrect", false);
                }
            }
            else {
                manager.closeConnection(false);
                return new Response("Error: incorrect or incomplete request", false);
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Response("Error: Unable to login user", false);
        }
    }

    private boolean hasAllValues(LoginRequest loginRequest) {
        if (loginRequest.getUsername().equals("") || loginRequest.getUsername() == null) {
            return false;
        }

        if (loginRequest.getPassword().equals("") || loginRequest.getPassword() == null) {
            return false;
        }

        return true;
    }
}

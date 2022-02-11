package model;

/**
 * The AuthToken class represents the data that is stored in the auth_token table.
 * It contains an authToken and a username
 * The authtoken allows the user to send requests to the server without needing to log in each time
 * This class contains getter and setter methods for each attribute
 */

public class AuthToken {
    private String authToken;
    private String username;

    /**
     * Constructor for creating an AuthToken object
     * @param authToken Unique authtoken
     * @param username Username that is associated with the authtoken
     */

    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

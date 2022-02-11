package services.requests;

/**
 * LoginRequest object holds the information that is needed to log in a user
 */

public class LoginRequest {
    private String username;
    private String password;

    /**
     * Constructor receives attribute values that are read from the JSON object
     * @param username Unique username for user
     * @param password User's password
     */

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

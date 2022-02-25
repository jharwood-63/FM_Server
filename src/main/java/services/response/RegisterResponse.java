package services.response;

/**
 * RegisterResponse holds the data and success status returned after a register is performed
 */

public class RegisterResponse extends Response {
    private String authToken;
    private String username;
    private String personID;

    /**
     * RegisterResponse constructor for creating a response to a register operation. If success is false, a message is created.
     * @param authToken Unique authoken
     * @param username Unique username for user
     * @param personID Unique person ID assigend to this user's generated person
     */

    public RegisterResponse(String authToken, String username, String personID, boolean success) {
        super(success);

        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}

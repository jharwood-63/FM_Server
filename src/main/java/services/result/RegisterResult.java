package services.result;

/**
 * RegisterResponse holds the data and success status returned after a register is performed
 */

public class RegisterResult extends Result {
    private String authtoken;
    private String username;
    private String personID;

    /**
     * RegisterResponse constructor for creating a response to a register operation. If success is false, a message is created.
     * @param authToken Unique authoken
     * @param username Unique username for user
     * @param personID Unique person ID assigend to this user's generated person
     */

    public RegisterResult(String authToken, String username, String personID, boolean success) {
        super(success);

        this.authtoken = authToken;
        this.username = username;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
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

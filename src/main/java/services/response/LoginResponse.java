package services.response;

/**
 * LoginResponse holds the data and success status returned after a login is performed
 */

public class LoginResponse extends Response {
    private String authtoken;
    private String username;
    private String personID;

    /**
     * LoginResponse constructor for creating a response to a login operation. If success is false, a message is created.
     * @param authtoken Unique authoken
     * @param username Unique username for the user
     * @param personID Unique person ID assigned to the user's generated person
     * @param success Status of the operation
     */

    public LoginResponse(String authtoken, String username, String personID, boolean success) {
        super(success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }
}

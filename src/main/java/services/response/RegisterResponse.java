package services.response;

/**
 * RegisterResponse holds the data and success status returned after a register is performed
 */

public class RegisterResponse {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success;
    private String message;

    /**
     * RegisterResponse constructor for creating a response to a register operation. If success is false, a message is created.
     * @param authtoken Unique authoken
     * @param username Unique username for user
     * @param personID Unique person ID assigend to this user's generated person
     * @param success Status of the operation
     */

    public RegisterResponse(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }
}

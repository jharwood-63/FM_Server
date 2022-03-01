package services.requests;

/**
 * PersonRequest object for requesting a specific person or all persons connected to the current user
 */

public class PersonRequest {
    private String personID;
    private String authtoken;

    public PersonRequest(String personID, String authtoken) {
        this.personID = personID;
        this.authtoken = authtoken;
    }

    /**
     * Default constructor for requesting all persons connected to the current user
     */

    public PersonRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }
}

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

    public PersonRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}

package services.requests;

/**
 * PersonRequest object for requesting a specific person or all persons connected to the current user
 */

public class PersonRequest {
    private String personID;

    /**
     * PersonRequest constructor for requesting a specific person
     * @param personID Unique person ID for the requested person
     */

    public PersonRequest(String personID) {
        this.personID = personID;
    }

    /**
     * Default constructor for requesting all persons connected to the current user
     */

    public PersonRequest() {

    }

    public String getPersonID() {
        return personID;
    }
}

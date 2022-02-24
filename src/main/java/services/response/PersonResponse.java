package services.response;

import model.Person;

/**
 * PersonResponse holds the data that is returned to the handler after a person request is made.
 * This is a response for both a single person request and an all persons request
 */

public class PersonResponse extends Response {
    private Person [] data;

    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    private boolean success;
    private String message;

    /**
     * Response constructor for a specific person request
     * @param associatedUsername Username of user to which this person belongs
     * @param personID Unique identifier for this person
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender Person's gender ("m" or "f")
     * @param fatherID Person ID of person's father (OPTIONAL)
     * @param motherID Person ID of person's mother (OPTIONAL)
     * @param spouseID Person ID of person's spouse (OPTIONAL)
     * @param success Status of operation
     */

    public PersonResponse(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    /**
     * Response constructor for an all person's request
     * @param data Array of persons
     * @param success Status of operation
     */

    public PersonResponse(Person [] data, boolean success) {
        this.data = data;
        this.success = success;
    }
}

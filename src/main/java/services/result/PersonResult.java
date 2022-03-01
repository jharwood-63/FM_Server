package services.result;

import model.Person;

/**
 * PersonResponse holds the data that is returned to the handler after a person request is made.
 * This is a response for both a single person request and an all persons request
 */

public class PersonResult extends Result {
    private Person [] data;

    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * Response constructor for a specific person request
     * @param person person object found in the query
     * @param success Status of operation
     */

    public PersonResult(Person person, boolean success) {
        super(success);
        this.associatedUsername = person.getAssociatedUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getPersonID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
    }

    /**
     * Response constructor for an all person's request
     * @param data Array of persons
     * @param success Status of operation
     */

    public PersonResult(Person [] data, boolean success) {
        super(success);
        this.data = data;
    }
}

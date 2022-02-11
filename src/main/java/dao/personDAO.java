package dao;

import model.Person;

/**
 * personDAO accesses data in the database
 */

public class personDAO {
    /**
     * Default constructor for personDAO
     */

    public personDAO() {

    }

    /**
     * Insert a new row in the person table
     * @param personID unique identifier for this person
     * @param associatedUsername username of user to which this person belongs
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender person's gender ("m" or "f")
     * @param fatherID person ID of person's father
     * @param motherID person ID of person's mother
     * @param spouseID person ID of person's spouse
     */

    private void insertPerson(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID){

    }

    /**
     * Deletes the row of the specified personID
     * @param personID the personID of the person that needs to be deleted
     */

    public void deletePerson(String personID) {

    }

    /**
     * Finds the person connected with the given personID
     * @param personID Unique identifier for the person
     * @return Person object
     */

    public Person find(String personID) {

        return null;
    }
}

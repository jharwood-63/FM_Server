package model;

import java.util.Set;

/**
 * The Person class represents the data that is stored in the person table
 * It contains a personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID
 * Each person is connected to a user through the associatedUsername attribute
 * This class contains getter and setter methods for each attribute
 */

public class Person {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public Person() {
        personID = "";
        associatedUsername = "";
        firstName = "";
        lastName = "";
        gender = "";
        fatherID = "";
        motherID = "";
        spouseID = "";
    }

    /**
     * Constructor for creating a Person object
     * @param personID Unique identifier for this person
     * @param associatedUsername Username of user to which this person belongs
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender Person's gender ("m" or "f")
     * @param fatherID Person ID of person's father (OPTIONAL)
     * @param motherID Person ID of person's mother (OPTIONAL)
     * @param spouseID Person ID of person's spouse (OPTIONAL)
     */

    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        Person compPerson = (Person)o;

        if (!hasSameComponents(compPerson)) {
            return false;
        }

        return true;
    }

    private boolean hasSameComponents(Person compPerson) {
        boolean isNullFather = checkIDs(compPerson.getFatherID(), this.fatherID);
        boolean isNullMother = checkIDs(compPerson.getMotherID(), this.motherID);
        boolean isNullSpouse = checkIDs(compPerson.getSpouseID(), this.spouseID);
        // father
        if (isNullFather && !isNullMother && !isNullSpouse) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getMotherID().equals(this.motherID) ||
                    !compPerson.getSpouseID().equals(this.spouseID)) {
                return false;
            }
        } // mother
        else if (isNullMother && !isNullFather && !isNullSpouse) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getFatherID().equals(this.fatherID) ||
                    !compPerson.getSpouseID().equals(this.spouseID)) {
                return false;
            }
        } // spouse
        else if (isNullSpouse && !isNullFather && !isNullMother) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getMotherID().equals(this.motherID) ||
                    !compPerson.getFatherID().equals(this.fatherID)) {
                return false;
            }
        } // father mother
        else if (isNullFather && isNullMother && !isNullSpouse) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getSpouseID().equals(this.spouseID)) {
                return false;
            }
        } // father spouse
        else if (isNullFather && isNullSpouse && !isNullMother) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getMotherID().equals(this.motherID)) {
                return false;
            }
        } // mother spouse
        else if (isNullMother && isNullSpouse && !isNullFather) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getFatherID().equals(this.fatherID)) {
                return false;
            }
        } // father mother spouse
        else if (isNullFather && isNullMother && isNullSpouse) {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender)) {
                return false;
            }
        }
        // none
        else {
            if (!compPerson.getPersonID().equals(this.personID) || !compPerson.getAssociatedUsername().equals(this.associatedUsername) ||
                    !compPerson.firstName.equals(this.firstName) || !compPerson.getLastName().equals(this.lastName) ||
                    !compPerson.getGender().equals(this.gender) || !compPerson.getFatherID().equals(this.fatherID) || !compPerson.getMotherID().equals(this.motherID) ||
                    !compPerson.getSpouseID().equals(this.spouseID)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the ids are null
     * @param compID A Spouse, Father, or Mother ID of the object being compared to this
     * @param thisID A Spouse, Father, or Mother ID of this object
     * @return Returns true if both parameters are null
     */

    private boolean checkIDs(String compID, String thisID) {
        if (compID == null && thisID == null) {
            return true;
        }

        return false;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isInSet(Set<Person> persons) {
        for (Person testPerson : persons) {
            if (this.equals(testPerson)) {
                return true;
            }
        }

        return false;
    }
}

package dao;

import model.Person;

import java.sql.*;

/**
 * personDAO accesses data in the database
 */

public class personDAO {
    private final Connection conn;

    /**
     * Constructor for personDAO initializes a connection to the database
     * @param conn Connection to the database
     */

    public personDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insert a new row in the person table
     * @param person A person object that will be inserted into the database
     */

    public boolean insertPerson(Person person) throws DataAccessException {
        boolean added = false;
        String sql = "INSERT INTO person (personID, AssociatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();

            added = true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }

        return added;
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

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

    public void insertPerson(Person person) throws DataAccessException {
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
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds the person connected with the given personID
     * @param personID Unique identifier for the person
     * @return Person object
     */

    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("AssociatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Clears the person table in the database
     */

    public void clearPerson() throws DataAccessException {
        String sql = "DELETE FROM person";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    public void deletePerson(String personID) throws DataAccessException {
        String sql = "DELETE FROM person WHERE personID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a person");
        }
    }
}

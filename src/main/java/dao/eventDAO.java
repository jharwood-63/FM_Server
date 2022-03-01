package dao;

import model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * EventDAO accesses data in the database
 */

public class eventDAO {
    private final Connection conn;

    /**
     * Initializes a connection to the database
     * @param conn Connection to the database
     */

    public eventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new row in the event table
     * @param event Event object that will be inserted into the database
     */

    public void insertEvent(Event event) throws DataAccessException {
        String sql = "INSERT INTO event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds the event tied to the given eventID
     * @param eventID Unique identifier
     * @return Event object
     */

    public Event findEvent(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    public Event findEvent(String personID, String eventType) throws DataAccessException {
        Event event = null;
        ResultSet rs;
        String sql = "SELECT * FROM event WHERE personID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("eventType").equalsIgnoreCase(eventType)) {
                    event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                            rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                            rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                            rs.getInt("year"));
                    break;
                }
            }
            return event;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Clears the event table in the database
     */

    public void clearEvent() throws DataAccessException{
        String sql = "DELETE FROM event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public void clearEvent(String username) throws DataAccessException {
        String sql = "DELETE FROM event WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an event");
        }
    }

    public void deleteEvent(String eventID) throws DataAccessException {
        String sql = "DELETE FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public Set<Event> findAll(String username) throws DataAccessException {
        Set<Event> events = new HashSet<>();
        ResultSet rs;
        String sql = "SELECT * FROM event WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                events.add(createEvent(rs));
            }

            return events;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error trying to find events associated with the username");
        }
    }

    private Event createEvent(ResultSet rs) throws SQLException {
        try {
            Event event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                    rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                    rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                    rs.getInt("year"));

            return event;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error creating event");
        }
    }
}

package dao;

import model.Event;

/**
 * EventDAO accesses data in the database
 */

public class eventDAO {
    /**
     * Default constructor for eventDAO
     */

    public eventDAO() {

    }
    /**
     * Inserts a new row in the event table
     * @param eventID Unique identifier for this event
     * @param associatedUsername Username of user to which this event belongs
     * @param personID ID of person to which this event belongs
     * @param latitude Latitude of event's location
     * @param longitude Longitude of event's location
     * @param country Country in which event occurred
     * @param city City in which event occurred
     * @param eventType Type of event
     * @param year Year in which event occurred
     */

    public void insertEvent(String eventID, String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {

    }

    /**
     * Deletes the row of the even with the specified eventID
     * @param eventID The eventID of the even that will be deleted
     */

    public void deleteEvent(String eventID) {

    }

    /**
     * Finds the event tied to the given eventID
     * @param eventID Unique identifier
     * @return Event object
     */

    public Event findEvent(String eventID) {

        return null;
    }
}

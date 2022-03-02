package model;

import java.util.Set;

/**
 * The Event class represents the data that is stored in the event table.
 * It contains an eventID, associateUsername, personID, latitude, longitude, country, city, eventType, and year
 * This stores specific data about each event that is created for each person
 * The personID connects the event to the person
 * The associatedUsername connects the event to the user
 * This class contains getter and setter methods for each attribute
 */

public class Event {
    private String eventID;
    private String associatedUsername;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event() {

    }

    /**
     * Constructor for creating an Event object
     * @param eventID Unique identifier for this event
     * @param associatedUsername Username of user to which this event belongs
     * @param personID ID of person to which this event belongs
     * @param latitude Latitude of event's location
     * @param longitude Longitude of event's location
     * @param country Country in which even occurred
     * @param city City in which event occurred
     * @param eventType Type of event
     * @param year Year in which event occurred
     */

    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
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

        Event compEvent = (Event)o;

        if (!hasSameComponents(compEvent)) {
            return false;
        }

        return true;
    }

    private boolean hasSameComponents(Event compEvent) {
        if (!compEvent.getEventID().equals(this.eventID) || !compEvent.getAssociatedUsername().equals(this.associatedUsername) ||
            !compEvent.getPersonID().equals(this.personID) || compEvent.getLatitude() != this.latitude || compEvent.getLongitude() != this.longitude ||
            !compEvent.getCountry().equals(this.country) || !compEvent.getCity().equals(this.city) || !compEvent.getEventType().equals(this.eventType) ||
            compEvent.getYear() != this.year) {
            return false;
        }

        return true;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isInSet(Set<Event> events) {
        for (Event testEvent : events) {
            if (this.equals(testEvent)) {
                return true;
            }
        }

        return false;
    }
}

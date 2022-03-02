package services.result;

import model.Event;

/**
 * EventResponse holds the message and success status
 */

public class EventResult extends Result {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private Float latitude;
    private Float longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    private Event [] data;

    /**
     * EventResponse constructor for a single event. If responseEvent is null, creates a message
     * @param responseEvent The event that was found in the operation
     */

    public EventResult(Event responseEvent, boolean success) {
        super(success);
        this.associatedUsername = responseEvent.getAssociatedUsername();
        this.eventID = responseEvent.getEventID();
        this.personID = responseEvent.getPersonID();
        this.latitude = responseEvent.getLatitude();
        this.longitude = responseEvent.getLongitude();
        this.country = responseEvent.getCountry();
        this.city = responseEvent.getCity();
        this.eventType = responseEvent.getEventType();
        this.year = responseEvent.getYear();
    }

    /**
     * EventResponse constructor for all events. if responseEvents is null, creates a message
     * @param responseEvents Array of events that were found in the database
     */

    public EventResult(Event [] responseEvents, boolean success) {
        super(success);
        this.data = responseEvents;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public Integer getYear() {
        return year;
    }

    public Event[] getData() {
        return data;
    }
}

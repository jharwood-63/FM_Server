package services.response;

import model.Event;

/**
 * EventResponse holds the message and success status
 */

public class EventResponse extends Response {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    private Event [] data;

    boolean success;
    String message;

    /**
     * EventResponse constructor for a single event. If responseEvent is null, creates a message
     * @param responseEvent The event that was found in the operation
     */

    public EventResponse(Event responseEvent) {
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

    public EventResponse(Event [] responseEvents) {
        this.data = responseEvents;
    }
}

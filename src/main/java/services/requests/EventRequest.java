package services.requests;

/**
 * The EventRequest object holds the information needed to request a specific event or all events
 */

public class EventRequest {
    private String eventID;

    /**
     * This constructor is used when an event request is made for a specific event
     * @param eventID
     */

    public EventRequest(String eventID) {
        this.eventID = eventID;
    }

    /**
     * The default constructor is used when an event request is made for all events
     */

    public EventRequest() {

    }

    public String getEventID() {
        return eventID;
    }
}

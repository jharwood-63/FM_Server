package services.requests;

/**
 * The EventRequest object holds the information needed to request a specific event or all events
 */

public class EventRequest {
    private String eventID;
    private String authtoken;

    /**
     * This constructor is used when an event request is made for a specific event
     * @param eventID
     */

    public EventRequest(String eventID, String authtoken) {
        this.eventID = eventID;
        this.authtoken = authtoken;
    }

    /**
     * The default constructor is used when an event request is made for all events
     */

    public EventRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}

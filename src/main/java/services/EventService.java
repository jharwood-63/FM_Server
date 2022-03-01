package services;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.authTokenDAO;
import dao.eventDAO;
import model.AuthToken;
import model.Event;
import services.requests.EventRequest;
import services.result.EventResult;
import services.result.Result;

import java.sql.Connection;
import java.util.Set;

/**
 * EventService class receives an EventRequest from the EventHandler
 * A specific event or all events are returned
 */

public class EventService {
    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     * Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     * @param eventRequest Specifies a specific EventID or asks for all events
     * @return EventResponse
     */

    public Result event(EventRequest eventRequest) {
        DatabaseManager manager = new DatabaseManager();

        try {
            Connection conn = manager.getConnection();
            eventDAO eventDAO = new eventDAO(conn);
            authTokenDAO authTokenDAO = new authTokenDAO(conn);
            Utility utility = new Utility();

            AuthToken authToken = authTokenDAO.find(eventRequest.getAuthtoken());
            if (authToken != null) {
                String authTokenUsername = authToken.getUsername();
                String eventID = eventRequest.getEventID();

                if (eventID != null) {
                    Event event = eventDAO.findEvent(eventID);

                    if (event != null) {
                        if (utility.isAssociated(event.getAssociatedUsername(), authTokenUsername)) {
                            manager.closeConnection(true);
                            return new EventResult(event, true);
                        }
                        else {
                            manager.closeConnection(false);
                            return new Result("Event requested is not associated with the requesting user", false);
                        }
                    }
                    else {
                        manager.closeConnection(false);
                        return new Result("Unable to find specified event in database", false);
                    }
                }
                else {
                    Set<Event> events = eventDAO.findAll(authTokenUsername);
                    Event[] eventArray = new Event[events.size()];
                    events.toArray(eventArray);

                    manager.closeConnection(true);
                    return new EventResult(eventArray, true);
                }
            }
            else {
                manager.closeConnection(false);
                return new Result("Invalid authtoken provided", false);
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            manager.closeConnection(false);
            return new Result("Error: unable to complete request", false);
        }
    }
}

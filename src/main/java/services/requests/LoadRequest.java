package services.requests;

import model.*;

/**
 * Clears all data from the database
 * Loads the user, person, and event data from the request body into the database
 */

public class LoadRequest {
    private User [] users;
    private Person [] persons;
    private Event [] events;

    /**
     * Constructor initializes the data members of this request object
     * @param users Array of new user objects to be loaded in
     * @param persons Array of new person objects to be loaded in
     * @param events Array of new event objects to be loaded in
     */

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}

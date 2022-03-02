package services.result;

/**
 * Holds the response data that will be returned with the result of the operation
 */

public class LoadResult extends Result {
    private int numUsers;
    private int numPersons;
    private int numEvents;

    /**
     * LoadResponse constructor creates a message and initializes the success status
     * @param success Status of the operation
     * @param numUsers Number of users added
     * @param numPersons Number of persons added
     * @param numEvents Number of events added
     */

    public LoadResult(int numUsers, int numPersons, int numEvents, boolean success) {
        super("Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents +
                " events to the database.", success);
        this.numUsers = numUsers;
        this.numPersons = numPersons;
        this.numEvents = numEvents;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public int getNumEvents() {
        return numEvents;
    }
}

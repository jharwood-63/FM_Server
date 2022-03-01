package services.result;

/**
 * Holds the response data that will be returned with the result of the operation
 */

public class LoadResult extends Result {

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
    }
}
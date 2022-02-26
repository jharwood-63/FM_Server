package services.response;

/**
 * Holds the response data that will be returned with the result of the operation
 */

public class LoadResponse extends Response {

    /**
     * LoadResponse constructor creates a message and initializes the success status
     * @param success Status of the operation
     * @param numUsers Number of users added
     * @param numPersons Number of persons added
     * @param numEvents Number of events added
     */

    public LoadResponse(boolean success, int numUsers, int numPersons, int numEvents) {
        super("PLACEHOLDER", success);
    }
}

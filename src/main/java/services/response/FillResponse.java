package services.response;

/**
 * FillResponse object holds data for a response to a fill operation
 */

public class FillResponse extends Response {
    private String message;
    private boolean success;

    /**
     * FillResponse constructor for creating a response to a fill operation. The message is different based on the success status.
     * @param success Status of the operation
     * @param numEvents Number of events added to the database
     * @param numPersons Number of persons added to the database
     */

    public FillResponse(boolean success, int numPersons, int numEvents) {
        this.success = success;
    }
}

package services.result;

/**
 * ClearResponse object holds the data for the response to a ClearRequest
 */

public class ClearResult extends Result {
    /**
     * ClearResponse constructor creates a ClearResponse object that holds a message and a success status
     * @param message Description of the outcome of the clear
     * @param success Status of the clear
     */

    public ClearResult(String message, boolean success) {
        super(message, success);
    }
}

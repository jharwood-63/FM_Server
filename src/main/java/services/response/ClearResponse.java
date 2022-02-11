package services.response;

/**
 * ClearResponse object holds the data for the response to a ClearRequest
 */

public class ClearResponse {
    private String message;
    private boolean success;

    /**
     * ClearResponse constructor creates a ClearResponse object that holds a message and a success status
     * @param message Description of the outcome of the clear
     * @param success Status of the clear
     */

    public ClearResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}

package services.response;

public class Response {
    /*
    * The classes that inherit from response should not have a message or success variable
    * and they should use these super constructors
     */
    private String message;
    private boolean success;

    public Response(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Response(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

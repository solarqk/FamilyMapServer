package Request;

/**
 * returns the result of whether clearing the DB was successful or not
 */
public class ClearRequest {
    /** if error, this is the error message */
    private String message;
    /** true for success and false for failure */
    private boolean success;

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

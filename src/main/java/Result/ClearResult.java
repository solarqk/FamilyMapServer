package Result;

/**
 * clear response
 */
public class ClearResult {
    /** success message*/
    private String message;
    /** success bool*/
    private boolean success;

    /**
     * Constructor for /clear
     *
     * @param message success or error message
     * @param success success or fail
     */
    public ClearResult(String message, boolean success) {
        this.message = message;
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

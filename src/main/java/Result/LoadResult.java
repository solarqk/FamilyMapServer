package Result;

/**
 * load response
 */
public class LoadResult {
    /**
     * reports success or error
     */
    private String message;

    /**
     * boolean
     */
    private boolean success;

    /**
     * Constructor for load request
     *
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public boolean isSuccess() {
        return success;
    }
}

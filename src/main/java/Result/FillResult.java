package Result;

/**
 * return result on whether the fill was successful or not
 */
public class FillResult {
    /**
     * success message
     */
    private String message;
    /**
     * success or fail
     */
    private boolean success;

    /**
     * returns whether the fill was successful or not
     *
     * @param message returns whether it was added or not
     * @param success true if done, false if not
     */
    public FillResult(String message, boolean success) {
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

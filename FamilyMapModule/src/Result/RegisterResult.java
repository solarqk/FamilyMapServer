package Result;


/**
 * Creates a new user account (user row in the database)
 * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a
 *          generations value of 4 and this new user’s username as parameters)
 * Logs the user in
 * Returns an authtoken
 */
public class RegisterResult {
    /** authtoken to be return after registering */
    private String authtoken;
    /** username to be returned after registering */
    private String username;
    /** personID to be returned after registering */
    private String personID;
    /** if theres an error, returns error */
    private String message;
    /** tells whether it was a success or not */
    private boolean success;

    /**
     * a success
     *
     * @param authtoken
     * @param username
     * @param personID
     */
    public RegisterResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * if the registering was a failure
     *
     * @param message will be error message
     * @param success should be false
     */
    public RegisterResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

package Result;

/**
 * returns the JSON String result of whether the user was loggin in or not
 */
public class LoginResult {

    /** authtoken to be return after logging in */
    private String authtoken;
    /** username to be return after logging in */
    private String username;
    /** personID to be return after logging in */
    private String personID;
    /** tells whether the login was a success or not */
    private boolean success;
    /** if user didn't login in, gives error message */
    private String message;

    /**
     * login successful
     *
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */
    public LoginResult(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    /**
     * constructor for failed login
     *
     * @param success tells if the login wasn't a success
     * @param message tells error
     *
     */
    public LoginResult(boolean success, String message) {
        this.success = success;
        this.message = message;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package Request;


import Model.UserModel;

/**
 * does login requests for users
 */
public class LoginRequest {
    /** username of user */
    private String username;

    /** password of user */
    private String password;

    /** used to compare to given data */
    private UserModel user;

    /**
     * deals with login requests
     *
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * constructor for login, but with null parameters
     */
    public LoginRequest() {
        username = null;
        password = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}

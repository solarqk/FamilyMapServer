package Model;


import java.util.Objects;

/**
 *  creates and pairs an authtoken to a user based on username
 */
public class AuthTokenModel {

    /** Unique authtoken */
    private String authtoken;
    /** Username that is associated with the authtoken */
    private String username;

    /**
     *
     * @param authtoken unique token used to authorize user
     * @param username associated with the user receiving authtoken
     */
    public AuthTokenModel(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public AuthTokenModel() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenModel oAuthToken = (AuthTokenModel) o;
        return oAuthToken.getAuthtoken().equals(authtoken) &&
                oAuthToken.getUsername().equals(username);
    }
}
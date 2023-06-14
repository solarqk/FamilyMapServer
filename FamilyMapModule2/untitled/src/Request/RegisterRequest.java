package Request;

import Model.UserModel;

/**
 * process clients request to register new user
 */
public class RegisterRequest {
    /** requests new user with username */
    private String username;
    /** requests new user with password */
    private String password;
    /** requests new user with email */
    private String email;
    /** requests new user with firstName */
    private String firstName;
    /** requests new user with lastName */
    private String lastName;
    /** requests new user with gender */
    private String gender;
    /** requests new user with personID */
    private String personID;


    /**
     * sets the users parameters to the database parameters
     *
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public RegisterRequest(UserModel userModel) {
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.email = userModel.getEmail();
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.gender = userModel.getGender();
        this.personID = userModel.getPersonID();
    }
    public RegisterRequest() {
        username = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}


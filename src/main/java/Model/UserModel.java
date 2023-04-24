package Model;


/**
 * stores user information from the database
 */
public class UserModel {

    /** Unique username for user */
    private String username;
    /** User’s password */
    private String password;
    /** User’s email address */
    private String email;
    /** User’s first name */
    private String firstName;
    /** User’s last name */
    private String lastName;
    /** User’s gender */
    private String gender;
    /** Unique Person ID assigned to this user’s generated Person */
    private String personID;


    public UserModel () {
        username = null;
        password = null;
    }


    /**
     *
     * @param username associated with the user
     * @param password password associated with the user
     * @param email email associated with the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param gender gender of the user
     * @param personID unique ID of the user
     */
    public UserModel(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof UserModel) {
            UserModel oUserModel = (UserModel) o;
            return oUserModel.getUsername().equals(getUsername()) &&
                    oUserModel.getPassword().equals(getPassword()) &&
                    oUserModel.getEmail().equals(getEmail()) &&
                    oUserModel.getFirstName().equals(getFirstName()) &&
                    oUserModel.getLastName().equals(getLastName()) &&
                    oUserModel.getGender().equals(getGender()) &&
                    oUserModel.getPersonID().equals(getPersonID());
        }
        else return false;
    }
}

package Request;


/**
 * how person request interacts with the database
 */

public class PersonRequest {
    private String personID;
    private String authtoken;


    /**
     * Constructor with personID and authtoken
     *
     * @param personID
     * @param authtoken
     *
     * @return PersonRequest object
     */
    public PersonRequest(String personID, String authtoken) {
        this.personID = personID;
        this.authtoken = authtoken;
    }

    /**
     * Constructor with only authtoken
     * @param authtoken
     */
    public PersonRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

}

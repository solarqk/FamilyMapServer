package Result;

import Model.PersonModel;

import java.util.List;

/**
 * result for whether person was successful or not
 */
public class PersonResult {
    /** Unique identifier for this person */
    private String personID;
    /** Username of user to which this person belongs */
    private String associatedUsername;
    /** Person’s first name */
    private String firstName;
    /** Person’s last name */
    private String lastName;
    /** Person’s gender */
    private String gender;  //either "f" or "m"
    /** Person ID of person’s father */
    private String fatherID;    //can be null
    /** Person ID of person’s mother */
    private String motherID;    //can be null
    /** Person ID of person’s spouse */
    private String spouseID;    //can be null
    /** success bool */
    private boolean success;

    private List<PersonModel> data;
    private List<PersonResult> dataPersonResult;
    /** success message*/
    private String message;


    /**
     * Constructor for PersonIDService
     *
     * @param pM PersonModel Object
     * @param success success
     */
    public PersonResult(PersonModel pM, boolean success) {
        this.personID = pM.getPersonID();
        this.associatedUsername = pM.getAssociatedUsername();
        this.firstName = pM.getFirstName();
        this.lastName = pM.getLastName();
        this.gender = pM.getGender();
        this.fatherID = pM.getFatherID();
        this.motherID = pM.getMotherID();
        this.spouseID = pM.getSpouseID();
        this.success = success;
    }

    /**
     * Constructor for PersonFamilyService
     *
     * @param info array of PersonModel objects
     * @param success
     */
    public PersonResult(List<PersonModel> info, boolean success) {
        this.data = info;
        this.success = success;
    }

    /**
     * Constructor for failed request
     *
     * @param message error message
     * @param success failed
     */
    public PersonResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<PersonModel> getData() {
        return data;
    }

    public List<PersonResult> getDataResult() { return dataPersonResult;}

    public void setData(List<PersonModel> info) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

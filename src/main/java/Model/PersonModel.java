package Model;


/**
 * create a person and adds to a family tree associated with user
 */
public class PersonModel {

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


    /**
     *
     * @param personID ID of the person
     * @param associatedUsername username that is associated with person
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender person's gender
     * @param fatherID id of the person's father
     * @param motherID id of the person's mother
     * @param spouseID id of the person's spouse
     */
    public PersonModel(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public PersonModel(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public PersonModel(String personID, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof PersonModel) {
            PersonModel oPersonModel = (PersonModel) o;
            return oPersonModel.getPersonID().equals(getPersonID()) &&
                    oPersonModel.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPersonModel.getFirstName().equals(getFirstName()) &&
                    oPersonModel.getLastName().equals(getLastName()) &&
                    oPersonModel.getGender().equals(getGender()) &&
                    oPersonModel.getFatherID().equals(getFatherID()) &&
                    oPersonModel.getMotherID().equals(getMotherID()) &&
                    oPersonModel.getSpouseID().equals(getSpouseID());
        }
        else return false;
    }
}
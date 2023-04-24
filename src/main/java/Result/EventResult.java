package Result;

import Model.EventModel;
import java.util.List;

/**
 * return result on whether event was successful or not
 */
public class EventResult {
    /** event's id */
    private String eventID;
    /** associatedUsername with event*/
    private String associatedUsername;
    /** person id associdated with event*/
    private String personID;
    /** latitude of event*/
    private float latitude;
    /** longitude of event */
    private float longitude;
    /** country event is in */
    private String country;
    /** city event is in*/
    private String city;
    /** type of event*/
    private String eventType;
    /** year event takes place*/
    private int year;
    /** success message*/
    String message;
    /** success bool*/
    boolean success;
    List<EventModel> data;

    /**
     * Constructor with individual parameters and a success bool
     *
     * @param eventID
     * @param username
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     * @param success
     */
    public EventResult(String eventID, String username, String personID, Float latitude, Float longitude,
                      String country, String city, String eventType, Integer year, boolean success) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }

    /**
     * Constuctor with a List of Eventmodels
     *
     * @param eventList
     */
    public EventResult(List<EventModel> eventList) {
        this.data = eventList;
        success = true;
    }

    /**
     * sets everything given an event, with a success bool
     *
     * @param event
     */
    public EventResult(EventModel event) {
        this.eventID = event.getEventID();
        this.associatedUsername = event.getAssociatedUsername();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
    }

    /**
     * sets everything given a event
     *
     * @param event
     * @param success
     */
    public EventResult(EventModel event, boolean success) {
        this.eventID = event.getEventID();
        this.associatedUsername = event.getAssociatedUsername();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
        this.success = success;
    }

    /**
     * return a failed message
     * @param message
     */
    public EventResult(String message) {
        this.message = message;
        success = false;
    }

    /**
     * return a failed message
     * @param success
     * @param message
     */
    public EventResult (boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public EventResult() {

    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public List<EventModel> getData() {
        return data;
    }

    public void setData(List<EventModel> data) {
        this.data = data;
    }
}

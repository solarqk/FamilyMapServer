package Model;

import java.util.Objects;

/**
 *  Event object with all its information
 */
public class EventModel {
    /** Unique identifier for this event */
    private String eventID;
    /** Username of user to which this event belongs */
    private String associatedUsername;
    /** ID of person to which this event belongs */
    private String personID;
    /** Latitude of event’s location */
    private float latitude;
    /** Longitude of event’s location */
    private float longitude;
    /** Country in which event occurred */
    private String country;
    /** City in which event occurred */
    private String city;
    /** Type of event */
    private String eventType;
    /** Year in which event occurred */
    private int year;

    /**
     *
     *
     * @param eventID unique ID for event
     * @param username username to which is associated with the event
     * @param personID personID for which the event is associated
     * @param latitude latitude of event
     * @param longitude longitude of event
     * @param country country in which the event takes place
     * @param city city in which the event takes place
     * @param eventType type of event
     * @param year year of which the event takes place
     */
    public EventModel(String eventID, String username, String personID, Float latitude, Float longitude,
                      String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
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

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventModel event = (EventModel) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }

}

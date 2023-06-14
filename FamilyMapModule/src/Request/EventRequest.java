package Request;


/**
 * EventID and Event request
 */
public class EventRequest {

    /**
     * ID of event
     */
    private String eventID;

    /**
     * authtoken
     */
    private String authToken;

    /**
     * Constructor for eventID
     *
     * @param eventID ID of wanted event
     */
    public EventRequest(String eventID, String authToken) {
        this.eventID = eventID;
        this.authToken = authToken;
    }

    /**
     * Constructor for event
     *
     */
    public EventRequest(String authToken) {
        this.authToken = authToken;
    }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

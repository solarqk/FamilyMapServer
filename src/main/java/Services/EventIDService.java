package Services;


import DAO.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Request.EventRequest;
import Result.EventResult;

import java.sql.Connection;

/**
 * implements eventID
 */
public class EventIDService {
    DatabaseDAO db;
    Connection conn;


    public EventIDService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
            System.out.print("Database connection opened via EventIDService.\n");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with EventIDService Accessing Database");
        }
    }

    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param request EventReqeust object
     * @return EventResult object
     */
    public EventResult event(EventRequest request) throws DataAccessException {
        try {
            String eventID = request.getEventID();
            String authToken = request.getAuthToken();
            EventDAO eDAO = new EventDAO(conn);
            EventModel event;
            AuthTokenDAO aDAO = new AuthTokenDAO(conn);
            AuthTokenModel bestAuth;

            try {
                bestAuth = aDAO.findTokenByToken(authToken);
                if (bestAuth == null) {
                    EventResult errorResult = new EventResult("Error: authToken invalid");
                    db.closeConnection(false);
                    System.out.print("Database connection closed: False via EventIDService.\n");
                    return errorResult;
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
                db.closeConnection(false);
                System.out.print("Database connection closed: False via EventIDService.\n");
                throw new DataAccessException("Error while finding a Authtoken by token");
            }
            try {
                event = eDAO.findByEventID(eventID);
            } catch (DataAccessException e) {
                e.printStackTrace();
                db.closeConnection(false);
                System.out.print("Database connection closed: False via EventIDService.\n");
                throw new DataAccessException("Error while finding an Event by eventID");
            }

            if (!bestAuth.getUsername().equals(event.getAssociatedUsername())) {
                db.closeConnection(false);
                System.out.print("Database connection closed: False via EventIDService.\n");
                String message = "Error: Requested event doesn't belong to this user.";
                return new EventResult(false, message);
            }
            db.closeConnection(true);
            System.out.print("Database connection closed: True via EventIDService.\n");
            EventResult eventResult = new EventResult(event, true);
            return eventResult;

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            System.out.print("Database connection closed: False via EventIDService.\n");
            throw new DataAccessException("Error with getEvent in EventIDService");
        }
    }
}

package Services;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseDAO;
import DAO.EventDAO;
import Model.AuthTokenModel;
import Model.EventModel;
import Request.EventRequest;
import Result.EventResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * implements event
 */
public class EventFamilyService {
    DatabaseDAO db;
    Connection conn;

    public EventFamilyService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
            System.out.print("Database connection opened via EventFamilyService.\n");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with EventFamilyService Accessing Database");
        }
    }
    /**
     * Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     *
     * @param request EventRequest object
     * @return EventResult object
     */
    public EventResult getUserEvents(EventRequest request) throws DataAccessException {
        EventResult result = new EventResult();

        try {
            EventDAO eDAO = new EventDAO(conn);
            AuthTokenDAO aDAO = new AuthTokenDAO(conn);
            List<EventModel> events;

            AuthTokenModel bestAuth;
            try {
                bestAuth = aDAO.findTokenByToken(request.getAuthToken());
                if (bestAuth == null) {
                    result.setMessage("Error: Can't find authToken");
                    System.out.print("Database connection closed: False via EventFamilyService.\n");
                    db.closeConnection(false);
                    return result;
                }
            } catch (DataAccessException e) {
                result.setMessage("Error: Can't find authToken");
                result.setSuccess(false);
                System.out.print("Database connection closed: False via EventFamilyService.\n");
                db.closeConnection(false);
                return result;
            }

            events = eDAO.findByUsername(bestAuth.getUsername());

            if (events == null) {
                result.setMessage("Error: Events null.");
            }
            if (events != null) {
                result = new EventResult(events);

            }
            db.closeConnection(false); //not changing anything in database
            System.out.print("Database connection closed: True via EventFamilyService.\n");

            return result;

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            System.out.print("Database connection closed: False via EventFamilyService.\n");
            throw new DataAccessException("Error encountered in EventFamilyService.");
        }
    }
}

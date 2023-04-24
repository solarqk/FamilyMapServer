package Services;
import DAO.*;
import Result.ClearResult;

import java.sql.Connection;

/**
 * handles request to delete all the information in the database
 */
public class ClearService {
    /** user objects to clear */
    UserDAO users;
    /** person objects to clear */
    PersonDAO persons;
    /** event objects to clear */
    EventDAO events;
    /** authToken objects to clear */
    AuthTokenDAO authTokens;


    Connection conn;

    public ClearService() {
    }


    /**
     * Deletes all data from database
     *
     * @return a ClearResult object on whether the DB was cleared or not
     */
    public ClearResult clear() throws DataAccessException {

        DatabaseDAO db = new DatabaseDAO();
        db.openConnection();
        ClearResult cr;

        try {
            db.clearTables();
            cr = new ClearResult( "Clear succeeded", true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            cr = new ClearResult("Clear failed", false);
            e.printStackTrace();
            db.closeConnection(false);
            throw new DataAccessException("Error occurred while clearing the database");
        }
        return cr;
    }
}

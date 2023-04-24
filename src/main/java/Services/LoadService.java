package Services;

import DAO.*;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * implements the load
 */
public class LoadService {
    DatabaseDAO db;
    Connection conn;

    public LoadService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
            System.out.print("Database connection opened via LoadService.\n");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with LoadServiceConstructor.");
        }
    }
    /**
     *
     * Clears all data from the database (just like the /clear API)
     * Loads the user, person, and event data from the request body into the database.
     *
     *
     * @param request
     * @return
     */
    public LoadResult load(LoadRequest request) throws DataAccessException {
        LoadResult loadResult;

        try {
            //clears the tables
            db.clearTables();
            //puts info back in tables
            populateClasses(request);
            //Successfully added X users, Y persons, and Z events to the database.”
            loadResult = new LoadResult("Successfully added " + request.getUsers().size() +
                    " users, " + request.getPersons().size() + " persons, and " + request.getEvents().size() + " events to the database.", true);
            db.closeConnection(true);
            return loadResult;
        } catch (DataAccessException e) {
            e.printStackTrace();
            LoadResult toReturn = new LoadResult(e.toString(), false);
            return toReturn;
        }
    }
    private void populateClasses(LoadRequest request) {
        UserDAO uDAO = new UserDAO(conn);
        EventDAO eDAO = new EventDAO(conn);
        PersonDAO pDAO = new PersonDAO(conn);
        try {
            for (UserModel user : request.getUsers()) {
                uDAO.insert(user);
            }
            for (EventModel event : request.getEvents()) {
                eDAO.insert(event);
            }
            for (PersonModel person : request.getPersons()) {
                pDAO.addPerson(person);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}

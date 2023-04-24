package Services;

import DAO.*;
import Model.*;
import Result.FillResult;
import Request.FillRequest;
import Services.FamilyCreation.*;


import java.sql.Connection;

/**
 * handles request to fill family tree for a user
 *
 * Populates the server's database with generated data for the specified username.
 * The required "username" parameter must be a user already registered with the server.
 * If there is any data in the database already associated with the given username, it is deleted.
 *
 * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated,
 * and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
 *
 * More details can be found in the earlier section titled “Family History Information Generation”
 */
public class FillService {
    DatabaseDAO db;
    Connection conn;

    private String message;
    /**
     * success or fail
     */
    private boolean success;

    public FillService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with FillService Accessing Database");
        }
    }

    public FillResult fill(FillRequest req) throws DataAccessException {
        UserDAO uDAO = new UserDAO(conn);
        EventDAO eDAO = new EventDAO(conn);
        PersonDAO pDAO = new PersonDAO(conn);
        EventGenerator generateEvents = new EventGenerator();
        FillResult result;

        try {
            UserModel user = uDAO.findUserByUsername(req.getUsername());
            if (user == null) {
                result = new FillResult("Error: User not found in fill", false);
                db.closeConnection(false);
                return result;
            }

            //if already in database, delete and fill with new data
            pDAO.deleteWithUsername(req.getUsername());
            eDAO.deleteWithUsername(req.getUsername());

            PersonModel person = userToPerson(user);
            EventModel birth = generateEvents.generateBirth(person, null);

            pDAO.addPerson(person);
            eDAO.insert(birth);

            GenerationGenerator generate = new GenerationGenerator(req.getGenerations(), conn);
            generate.generateParents(person, 0);

            int i = (int) Math.pow(2.0D, (req.getGenerations() + 1)) - 1;
            int j = i * 2;

            result = new FillResult("Successfully added " + i +
                    " persons and " + j + " events to the database.", true);
            db.closeConnection(true);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with FillService");
        }
    }

    private PersonModel userToPerson(UserModel user) {
        return new PersonModel(user.getPersonID(), user.getUsername(),
                user.getFirstName(), user.getLastName(), user.getGender());
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
}


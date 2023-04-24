package Services;

import DAO.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;
import Request.PersonRequest;
import Result.PersonResult;
import passoffmodels.Person;

import java.sql.Connection;

/**
 * implements the PersonID
 */
public class PersonIDService {
    DatabaseDAO db;
    Connection conn;
    String personID;
    String authToken;
    AuthTokenModel bestAuth;
    PersonDAO pDAO;
    PersonModel bestPerson;
    PersonResult result;


    public PersonIDService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
            System.out.print("Database connection opened via personIDService.\n");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with PersonIDConstructor.");
        }
    }


    /**
     * Returns the single Person object with the specified ID (if the person is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param request
     * @return
     */
    public PersonResult person(PersonRequest request) throws DataAccessException {
        try {
            String username;
            personID = request.getPersonID();
            authToken = request.getAuthtoken();
            UserDAO uDAO = new UserDAO(conn);
            UserModel bestUser;
            AuthTokenDAO aDAO = new AuthTokenDAO(conn);

            try {
                bestUser = uDAO.findUserById(request.getPersonID());
                if (bestUser == null) {
                    db.closeConnection(false);
                    System.out.print("Database connection closed: False via personIDService.\n");
                    return new PersonResult("Error encountered with finding Authtoken.", false);
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
                db.closeConnection(false);
                System.out.print("Database connection closed: False via personIDService.\n");
                throw new DataAccessException("Error encountered while finding username in personIDService.");
            }

            try {
                bestAuth = aDAO.findTokenByToken(authToken);
                if (bestAuth == null) {
                    db.closeConnection(false);
                    System.out.print("Database connection closed: False via personIDService.\n");
                    return new PersonResult("Error encountered with finding Authtoken", false);
                }
            } catch (DataAccessException e) {
                db.closeConnection(false);
                System.out.print("Database connection closed: False via personIDService.\n");
                return new PersonResult("Error encountered with finding Authtoken", false);
            }

            pDAO = new PersonDAO(conn);

            try {
                bestPerson = pDAO.findPersonByID(personID);
                if (bestPerson == null) {
                    db.closeConnection(false);
                    System.out.print("Database connection closed: False via personIDService.\n");
                    return new PersonResult("Error encountered with finding Person", false);
                }
            } catch (DataAccessException e) {
                db.closeConnection(false);
                System.out.print("Database connection closed: False via personIDService.\n");
                String message = "Error encountered with finding personID";
                return new PersonResult(message, false);
            }
            if (!bestAuth.getUsername().equals(bestPerson.getAssociatedUsername())) {
                db.closeConnection(false);
                System.out.print("Database connection closed: False via personIDService.\n");
                String message = "Error: Requested person doesn't belong to this user";
                return new PersonResult(message, false);
            }
            result = new PersonResult(bestPerson, true);
            db.closeConnection(true);
            System.out.print("Database connection closed: True via personIDService.\n");
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.print("Database connection closed: False via personIDService.\n");
            db.closeConnection(false);
        }
        return null;
    }
}

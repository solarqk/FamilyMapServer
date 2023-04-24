package Services;

import DAO.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;
import Request.PersonRequest;
import Result.LoginResult;
import Result.PersonResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 * implements personFamily
 */
public class PersonFamilyService {
    DatabaseDAO db;
    Connection conn;

    public PersonFamilyService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with PersonFamilyConstructor.");
        }
    }


    /**
     * Returns ALL family members of the current user.
     * The current user is determined by the provided authtoken.
     *
     * @param request PersonRequest request
     * @return PersonResult object
     */
    public PersonResult getFamily(PersonRequest request) throws DataAccessException {
        AuthTokenDAO aDAO = new AuthTokenDAO(conn);
        PersonDAO pDAO = new PersonDAO(conn);
        PersonModel bestPerson;
        List<PersonModel> family;
        AuthTokenModel bestAuth;
        PersonResult result;
        try {
            bestAuth = aDAO.findTokenByToken(request.getAuthtoken());

            if (bestAuth == null) {
                PersonResult errorResult = new PersonResult("Error: Invalid authToken.", false );
                db.closeConnection(false);
                return errorResult;
            }

            bestPerson = pDAO.findPersonByUsername(bestAuth.getUsername());
            if (bestPerson == null) {
                PersonResult errorResult = new PersonResult("Error: Invalid personID.", false );
                db.closeConnection(false);
                return errorResult;
            }

            family = pDAO.findAllWithUsername(bestPerson.getAssociatedUsername());
            result = new PersonResult(family, true);
            db.closeConnection(true);
            return result;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: Incorrect username or password.");
        }
    }
}

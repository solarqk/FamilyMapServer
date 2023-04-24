package Services;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseDAO;
import DAO.UserDAO;
import Model.AuthTokenModel;
import Model.UserModel;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * implements the login
 */
public class UserLoginService {
    DatabaseDAO db;
    Connection conn;

    /**
     * Logs the user in
     * Returns an authtoken.
     *
     * @param request
     * @return
     */
    public LoginResult login(LoginRequest request) throws Exception {
        db = new DatabaseDAO();
        conn = db.getConnection();
        System.out.print("Database connection opened via UserLoginService.\n");
        try {

            //check the username and password
            UserDAO uDAO = new UserDAO(conn);
            UserModel matchUser;
            try {
                matchUser = uDAO.findUserByUsername(request.getUsername());
                if (matchUser == null) {
                    LoginResult errorResult = new LoginResult(false, "Error: Incorrect username or password.");
                    db.closeConnection(false);
                    System.out.print("Database connection closed: False via UserLoginService.\n");

                    return errorResult;
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
                throw new DataAccessException("Error: Incorrect username or password.");
            }

            if (!matchUser.getPassword().equals(request.getPassword())) {
                throw new Exception("Error: Incorrect username or password.");
            }
            //create authToken and adds to table
            AuthTokenDAO aDAO = new AuthTokenDAO(conn);
            String authToken = UUID.randomUUID().toString();
            AuthTokenModel bestAuth = new AuthTokenModel(authToken, request.getUsername());
            aDAO.addToken(bestAuth);

            db.closeConnection(true);
            System.out.print("Database connection closed: True via UserLoginService.\n");

            return new LoginResult(authToken, matchUser.getUsername(), matchUser.getPersonID(), true);

        } catch (Exception e) {
            db.closeConnection(false);
            return new LoginResult(false, "Error: " + e);
        }
    }
}

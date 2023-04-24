package Services;

import DAO.DataAccessException;
import DAO.DatabaseDAO;
import DAO.UserDAO;
import Model.UserModel;
import Request.LoginRequest;
import Result.LoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserLoginServiceTest {
    DatabaseDAO db;
    Connection conn;
    UserModel bestUser;
    UserDAO uDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        conn = db.getConnection();
        db.clearDatabase();

        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        uDAO = new UserDAO(conn);
        uDAO.insert(bestUser);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loginPass() throws Exception {
        LoginRequest request = new LoginRequest(bestUser.getUsername(), bestUser.getPassword());
        UserLoginService userLogin = new UserLoginService();
        LoginResult result = userLogin.login(request);

        assertTrue(result.isSuccess());
        assertEquals(bestUser.getUsername(), result.getUsername());
        assertEquals(bestUser.getPersonID(), result.getPersonID());
        assertNotNull(result.getAuthtoken());
    }

    @Test
    public void loginWrongUsername() throws Exception {

        //Send login request with incorrect username
        LoginRequest request = new LoginRequest("unGamer", bestUser.getPassword());
        UserLoginService loginService = new UserLoginService();
        LoginResult result = loginService.login(request);

        //Check for failure and correct message
        assertFalse(result.isSuccess());
        assertEquals("Error: Incorrect username or password.", result.getMessage());
    }

    @Test
    public void loginWrongPassword() throws Exception {

        //Send login request with incorrect password
        LoginRequest request = new LoginRequest(bestUser.getUsername(), "unGamerPassword");
        UserLoginService loginService = new UserLoginService();
        LoginResult result = loginService.login(request);

        //Check for failure and correct message
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error"));
    }
}

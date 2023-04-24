package Services;
import DAO.*;
import Model.*;
import Request.PersonRequest;
import Result.PersonResult;
import Result.RegisterResult;
import Request.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRegisterServiceTest {
    private DatabaseDAO db;
    private Connection conn;
    private UserDAO uDAO;
    private UserModel bestUser;
    RegisterResult compareResult;
    RegisterRequest request;
    UserRegisterService r;
    UserModel userCompare;

    PersonModel bestPerson;


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        request = new RegisterRequest("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        bestPerson = new PersonModel("gamerID", "gamer", "Epic", "Gamer",
                "m", "toBeReplaced", "toBeReplacedMother", "epicWife");
        Connection conn = db.getConnection();
        System.out.print("Database connection opened via UserRegisterServiceTest.\n");
        db.clearTables();
        db.closeConnection(true);
        System.out.print("Database connection closed: True via UserRegisterServiceTest.\n");
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        System.out.print("Database connection opened via UserRegisterServiceTest.\n");
        db.clearTables();
        db.closeConnection(true);
        System.out.print("Database connection closed: True via UserRegisterServiceTest.\n");
    }

    @Test
    public void registerPass() throws DataAccessException {

        r = new UserRegisterService();
        r.register(request); //should add the user to the database

        Connection conn = db.getConnection();
        uDAO = new UserDAO(conn);

        userCompare = uDAO.findUserByUsername(bestUser.getUsername());
        db.closeConnection(true);

        assertEquals(bestUser.getUsername(), userCompare.getUsername());
    }

    @Test
    public void registerFail() throws DataAccessException {

        Connection conn = db.getConnection();
        System.out.print("Database connection opened via UserRegisterServiceTest.\n");

        uDAO = new UserDAO(conn);
        uDAO.insert(bestUser); //has the same username as the request

        db.closeConnection(true);
        System.out.print("Database connection closed: True via UserRegisterServiceTest.\n");

        r = new UserRegisterService();

        compareResult = r.register(request);
        RegisterResult test = new RegisterResult("Error encountered while inserting user to the database.", false);
        assertEquals(test.getMessage(), compareResult.getMessage());
    }

}

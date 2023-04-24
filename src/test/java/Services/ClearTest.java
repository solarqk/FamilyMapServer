package Services;
import DAO.*;
import Model.*;
import Result.ClearResult;
import Result.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class ClearTest {
    private DatabaseDAO db;
    private UserModel bestUser;
    private UserDAO uDao;
    private PersonModel bestPerson;
    private PersonDAO pDao;
    private AuthTokenModel bestAuth;
    private AuthTokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();

        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        bestPerson = new PersonModel("IDofPerson", "gamerUsername", "Epic", "Gamer",
                "m", "bigDaddy", "bigMommy", "bigSpouse");
        bestAuth = new AuthTokenModel("AUTHTOKEN", "username");
        Connection conn = db.getConnection();

        db.clearTables();
        uDao = new UserDAO(conn);
        aDao = new AuthTokenDAO(conn);
        pDao = new PersonDAO(conn);

        pDao.addPerson(bestPerson);
        aDao.addToken(bestAuth);
        uDao.insert(bestUser);
        db.closeConnection(true);
    }

    @Test
    void clearData() throws DataAccessException {
        ClearService clearServ = new ClearService();
        ClearResult cr = new ClearResult("Clear succeeded", true);
        ClearResult test = clearServ.clear();
        assertEquals(cr.getMessage(), test.getMessage());
    }

    @Test
    void clearDataFail() throws DataAccessException {
        ClearService clearServ = new ClearService();
        ClearResult cr = new ClearResult("Clear failed.", false );
        ClearResult test = clearServ.clear();
        assertNotEquals(cr.getMessage(), test.getMessage());
    }
}

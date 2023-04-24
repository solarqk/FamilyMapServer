package Services;

import DAO.*;
import Model.*;

import Request.FillRequest;
import Result.FillResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class FillServiceTest {
    DatabaseDAO db;
    Connection conn;
    UserDAO uDAO;
    UserModel bestUser;
    FillRequest request;
    FillResult result;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        conn = db.getConnection();
        db.clearTables();

        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        uDAO = new UserDAO(conn);
    }

    @Test
    void fillPass() throws DataAccessException {
        uDAO.insert(bestUser);
        db.closeConnection(true);

        request = new FillRequest("/fill/gamer/4");
        FillService fillS = new FillService();
        result = fillS.fill(request);

        assertTrue(result.isSuccess());

        FillResult testRes = new FillResult("Successfully added 31 persons and 62 events to the database.", true);
        assertEquals(testRes.getMessage(), result.getMessage());
    }

    @Test
    void fillFail() throws DataAccessException {
        uDAO.insert(bestUser);
        db.closeConnection(true);
        request = new FillRequest("/fill/gamer/4");

        FillService fillS = new FillService();
        result = fillS.fill(request);

        assertTrue(result.isSuccess());
        FillResult testRes = new FillResult("Error encountered while filling the database", false);
        assertNotEquals(testRes.getMessage(), result.getMessage());
    }
}

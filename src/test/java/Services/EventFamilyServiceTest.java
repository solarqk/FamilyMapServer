package Services;

import DAO.DataAccessException;
import DAO.DatabaseDAO;
import Model.UserModel;
import Request.EventRequest;
import Request.RegisterRequest;
import Result.EventResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventFamilyServiceTest {
    DatabaseDAO db;
    Connection conn;
    UserModel bestUser;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        System.out.print("Database connection opened via EventFamilyServiceTest1.\n");
        db.clearTables();

        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");

        db.closeConnection(true);
        System.out.print("Database connection closed: True via EventFamilyServiceTest1.\n");
    }

    @Test
    public void getUserEventsPass() throws DataAccessException {
        UserRegisterService registerService = new UserRegisterService();
        RegisterRequest rRequest = new RegisterRequest(bestUser);
        RegisterResult rResult = registerService.register(rRequest);

        String authString = rResult.getAuthtoken();

        EventFamilyService eventFamilyService = new EventFamilyService();
        EventRequest eRequest = new EventRequest(authString);
        EventResult eResult = eventFamilyService.getUserEvents(eRequest);

        assertTrue(eResult.isSuccess());
        assertNotNull(eResult.getData());
        assertEquals(91, eResult.getData().size());
    }

    @Test
    public void getUserEventsFail() throws DataAccessException {
        String authString = "notARealToken";

        EventFamilyService eventFamilyService = new EventFamilyService();
        EventRequest eRequest = new EventRequest(authString);
        EventResult eResult = eventFamilyService.getUserEvents(eRequest);

        assertFalse(eResult.isSuccess());
        assertNull(eResult.getData());
        assertEquals("Error: Can't find authToken", eResult.getMessage());
    }
}

package Services;
import DAO.*;
import Model.*;


import Request.EventRequest;
import Result.EventResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class EventIDServiceTest {
    private DatabaseDAO db;
    private EventModel bestEvent;
    private AuthTokenModel bestAuth;
    private EventDAO eDAO;
    private AuthTokenDAO aDAO;
    private EventRequest eventRequest;
    private EventResult eventResult;
    EventIDService eventIDService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        System.out.print("Database connection opened via EventIDServiceTest.\n");


        bestEvent = new EventModel("eventID1", "gamer", "gamerID",
                .0F, .03F, "USA", "Detroit", "birth", 2000);
        bestAuth = new AuthTokenModel("gamerToken","gamer");
        Connection conn = db.getConnection();

        db.clearTables();
        eDAO = new EventDAO(conn);
        aDAO = new AuthTokenDAO(conn);


        eDAO.insert(bestEvent);
        aDAO.addToken(bestAuth);
        db.closeConnection(true);
        System.out.print("Database connection closed: True via EventIDServiceTest.\n");
    }

    @Test
    public void eventPass() throws DataAccessException {
        eventRequest = new EventRequest("eventID1", "gamerToken");
        eventIDService = new EventIDService();
        eventResult = eventIDService.event(eventRequest);

        assertNotNull(eventResult);
        assertEquals(bestEvent.getEventID(), eventResult.getEventID());
    }

    @Test
    public void eventFail() throws DataAccessException {
        db.getConnection();
        db.clearTables();   //clears out all the tables, so, search should fail
        db.closeConnection(true);

        eventRequest = new EventRequest(bestEvent.getEventID(), bestAuth.getAuthtoken());
        eventIDService = new EventIDService();
        String assertEvent = eventIDService.event(eventRequest).getEventID();
        assertNull(assertEvent);
    }
}

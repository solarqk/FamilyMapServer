package DAO;

import Model.EventModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private DatabaseDAO db;
    private EventModel bestEvent;
    private EventModel birthEvent;
    private EventDAO eDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new DatabaseDAO();
        // and a new event with random data
        bestEvent = new EventModel("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        birthEvent = new EventModel("birthID", "Garth", "Garth123",
                40f, 40f, "China", "Beijing",
                "birth", 2000);
        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDAO = new EventDAO(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDAO.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDAO.insert(bestEvent);
        // Let's use a find method to get the event that we just put in back out.
        EventModel compareTest = eDAO.findByEventID(bestEvent.getEventID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        eDAO.insert(bestEvent);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> eDAO.insert(bestEvent));
    }
    @Test
    public void findByEventIDPass() throws DataAccessException {
        assertNull(eDAO.findByEventID(bestEvent.getEventID()));
        eDAO.insert(bestEvent);
        assertNotNull(eDAO.findByEventID(bestEvent.getEventID()));
    }

    @Test
    public void findByEventIDFail() throws DataAccessException {
        assertNull(eDAO.findByEventID(bestEvent.getEventID()));
    }

    @Test
    public void findByUsernamePass() throws DataAccessException {
        List<EventModel> eventList = new ArrayList<>();
        eventList.add(bestEvent);
        eDAO.insert(bestEvent);
        assertNotNull(eDAO.findByUsername(bestEvent.getAssociatedUsername()));
    }

    @Test
    public void findByUsernameFail() throws DataAccessException {
        List<EventModel> compareTest = eDAO.findByUsername(bestEvent.getAssociatedUsername());
        List<EventModel> emptyEventList = new ArrayList<>();
        assertEquals(compareTest, emptyEventList);
    }

    @Test
    public void clear() throws DataAccessException {
        eDAO.insert(bestEvent);
        eDAO.clear();
        assertNull(eDAO.findByEventID(bestEvent.getEventID()));
    }

    @Test
    public void deleteWithUsernamePass() throws DataAccessException {
        eDAO.insert(bestEvent);
        assertNotNull(eDAO.findByUsername(bestEvent.getAssociatedUsername()));

        eDAO.deleteWithUsername(bestEvent.getAssociatedUsername());
        assertEquals(eDAO.findByUsername(bestEvent.getAssociatedUsername()).size(),0);
    }

    @Test
    public void findBirthPass() throws DataAccessException {
        eDAO.insert(birthEvent);
        EventModel compareTest = eDAO.findBirth(birthEvent.getPersonID());
        assertEquals("birth", compareTest.getEventType());
    }

    @Test
    public void findBirthFail() throws DataAccessException {
        eDAO.insert(bestEvent);
        EventModel compareTest = eDAO.findBirth(bestEvent.getPersonID());
        assertNull(eDAO.findBirth(bestEvent.getEventType()));
    }
}

package Services;

import DAO.DataAccessException;
import DAO.DatabaseDAO;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoadRequest;
import Result.LoadResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadServiceTest {
    DatabaseDAO db;
    Connection conn;
    UserModel bestUser;
    PersonModel bestPerson;
    EventModel bestEvent;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        conn = db.getConnection();
        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        bestPerson = new PersonModel("gamerID", "gamer", "Epic", "Gamer",
                "m", "toBeReplaced", "toBeReplacedMother", "epicWife");
        bestEvent = new EventModel("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        db.clearTables();
    }

    @Test
    public void loadPass() throws DataAccessException {
        List<UserModel> users = new ArrayList<>();
        List<EventModel> events = new ArrayList<>();
        List<PersonModel> people = new ArrayList<>();

        users.add(bestUser);
        events.add(bestEvent);
        people.add(bestPerson);
        db.closeConnection(true);

        LoadRequest request = new LoadRequest(users, people, events);
        LoadService loadService = new LoadService();
        LoadResult result = loadService.load(request);

        LoadResult expectedResult = new LoadResult("Successfully added 1 users, 1 persons, and 1 events to the database.", true);
        assertEquals(expectedResult.getMessage(), result.getMessage());
    }

    @Test
    public void testWithEmptyDatabase() throws DataAccessException {
        LoadRequest request = new LoadRequest(null);
        LoadService loadService = new LoadService();
        LoadResult result = loadService.load(request);

        LoadResult expectedResult = new LoadResult("DAO.DataAccessException: SQL Error encountered while clearing tables", false);
        assertEquals(expectedResult.getMessage(), result.getMessage());
        db.closeConnection(true);
    }
}

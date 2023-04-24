package Services;

import DAO.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;
import Request.PersonRequest;
import Request.RegisterRequest;
import Result.PersonResult;
import Result.RegisterResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PersonFamilyServiceTest {
    private DatabaseDAO db;
    private Connection conn;
    private PersonModel bestPerson, normalPerson;
    private UserModel bestUser;
    private AuthTokenModel bestAuth;
    private String authString;
    private AuthTokenDAO aDAO;
    private UserDAO uDAO;
    private PersonDAO pDAO;
    private PersonResult result;
    private PersonRequest request;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        Connection conn = db.getConnection();
        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        bestPerson = new PersonModel("gamerID", "gamer", "Epic", "Gamer",
                "m", "toBeReplaced", "toBeReplacedMother", "epicWife");
        normalPerson = new PersonModel("IDofPerson2", "gamer", "notEpic", "UnGamer",
                "f", "toBeReplaced2", "toBeReplacedMother2", "normalHusband2");
        bestAuth = new AuthTokenModel("GamerToken", "gamer");
        request = new PersonRequest("gamerID", "GamerToken");
        db.clearTables();
        pDAO = new PersonDAO(conn);
        aDAO = new AuthTokenDAO(conn);
        uDAO = new UserDAO(conn);
    }

    @Test
    void getFamilyPass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        pDAO.addPerson(normalPerson);
        aDAO.addToken(bestAuth);
        uDAO.insert(bestUser);

        List<PersonModel> family = new ArrayList<>();
        family.add(bestPerson);
        family.add(normalPerson);

        PersonFamilyService service = new PersonFamilyService();
        db.closeConnection(true);

        PersonResult result = service.getFamily(request);
        assertEquals(family.get(0), result.getData().get(0));
        assertEquals(family.get(1), result.getData().get(1));
    }

    @Test
    void getFamilyFail() throws DataAccessException {

        aDAO.addToken(bestAuth);
        uDAO.insert(bestUser);

        List<PersonModel> personList = new ArrayList<>();
        personList.add(bestPerson);
        personList.add(normalPerson);

        PersonFamilyService service = new PersonFamilyService();
        db.closeConnection(true);

        PersonResult result = service.getFamily(request);
        assertNotEquals(personList, result.getData());
    }
}

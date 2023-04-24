package Services;
import DAO.*;
import Model.AuthTokenModel;
import Model.PersonModel;
import Model.UserModel;
import Request.PersonRequest;
import Request.RegisterRequest;
import Result.PersonResult;
import Result.RegisterResult;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PersonIDServiceTest {
    DatabaseDAO db;
    Connection conn;
    UserModel bestUser, normalUser;
    String authString;
    UserDAO uDAO;

    PersonModel bestPerson, normalPerson;
    PersonIDService service;
    PersonRequest personRequest;
    PersonResult personResult;
    PersonModel pFound;
    AuthTokenModel bestAuthModel;



    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        conn = db.getConnection();

        db.openConnection();
        System.out.print("Database connection opened via PersonIDServiceTest.\n");
        db.clearDatabase();
        db.closeConnection(true);
        System.out.print("Database connection closed: True via PersonIDServiceTest.\n");

        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        normalUser = new UserModel("unGamer", "unGamer123", "unGamer@gmail.com", "notEpic", "Gamer", "f", "unGamerID");
        bestPerson = new PersonModel("gamerID", "gamer", "Epic", "Gamer",
                "m", "toBeReplaced", "toBeReplacedMother", "epicWife");
        normalPerson = new PersonModel("unGamerID", "unGamer", "notEpic", "UnGamer",
                "f", "toBeReplaced2", "toBeReplacedMother2", "normalHusband2");
        bestAuthModel = new AuthTokenModel("gamerToken", "gamer");


    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void personIDPass() throws DataAccessException {
        Connection conn = db.getConnection();
        uDAO = new UserDAO(conn);
        AuthTokenDAO aDAO = new AuthTokenDAO(conn);
        uDAO.insert(bestUser);
        aDAO.addToken(bestAuthModel);
        PersonDAO pDAO = new PersonDAO(conn);
        pDAO.addPerson(bestPerson);
        db.closeConnection(true);
        authString = bestAuthModel.getAuthtoken();

        service = new PersonIDService();
        personRequest = new PersonRequest(bestPerson.getPersonID(), authString);
        personResult = service.person(personRequest);
        pFound = new PersonModel(personResult.getPersonID(), personResult.getAssociatedUsername(),
                personResult.getFirstName(), personResult.getLastName(), personResult.getGender(),
                personResult.getFatherID(), personResult.getMotherID(), personResult.getSpouseID());

        assertNotNull(pFound);
        assertNotNull(bestPerson);
        assertEquals(bestPerson, pFound);
    }

    @Test
    public void personIDFail() throws DataAccessException {
        String randomAuthToken = UUID.randomUUID().toString();
        PersonIDService service = new PersonIDService();
        PersonRequest pRequest = new PersonRequest(bestPerson.getPersonID(), randomAuthToken);
        PersonResult pResult = service.person(pRequest);

        assertFalse(pResult.isSuccess());
        assertEquals("Error encountered with finding Authtoken.", pResult.getMessage());

    }

}

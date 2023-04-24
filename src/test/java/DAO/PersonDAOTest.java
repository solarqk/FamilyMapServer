package DAO;

import Model.PersonModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class PersonDAOTest {
    private DatabaseDAO db;
    private PersonModel bestPerson;
    private PersonModel fatherBestPerson;
    private PersonModel motherBestPerson;
    private PersonDAO pDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        bestPerson = new PersonModel("IDofPerson", "gamerUsername", "Epic", "Gamer",
                "m", "toBeReplaced", "toBeReplacedMother", "epicWife");
        fatherBestPerson = new PersonModel("epicDaddy", "Fathers_Username", "Dad", "Gamer",
                "m", "epicPGranddaddy", "PGrandmother", "epicMommy");
        motherBestPerson = new PersonModel("epicMommy", "Mothers_Username", "Mom", "Gamer",
                "f", "MGranddaddy", "MGrandmother", "epicDaddy");
        Connection conn = db.getConnection();
        pDAO = new PersonDAO(conn);
        pDAO.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void addPass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        PersonModel compareTest = pDAO.findPersonByID(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        assertThrows(DataAccessException.class, () -> pDAO.addPerson(bestPerson));
    }

    @Test
    public void findWithIDPass() throws DataAccessException {
        assertNull(pDAO.findPersonByID(bestPerson.getPersonID()));
        pDAO.addPerson(bestPerson);
        assertNotNull(pDAO.findPersonByID(bestPerson.getPersonID()));
    }

    @Test
    public void findWithIDFail() throws DataAccessException {
        assertNull(pDAO.findPersonByID(bestPerson.getPersonID()));
    }

   @Test
    public void findWithUsernamePass() throws DataAccessException {
        assertNull(pDAO.findPersonByUsername(bestPerson.getAssociatedUsername()));

        pDAO.addPerson(bestPerson);
        assertNotNull(pDAO.findPersonByUsername(bestPerson.getAssociatedUsername()));
    }

    @Test
    public void findWithUsernameFail() throws DataAccessException {
        assertNull(pDAO.findPersonByUsername(bestPerson.getAssociatedUsername()));
    }

    @Test
    public void clear() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        pDAO.clear();
        assertNull(pDAO.findPersonByID(bestPerson.getPersonID()));
    }

    @Test
    public void deleteWithUsernamePass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        assertNotNull(pDAO.findPersonByUsername(bestPerson.getAssociatedUsername()));

        pDAO.deleteWithUsername(bestPerson.getAssociatedUsername());
        assertNull(pDAO.findPersonByUsername(bestPerson.getAssociatedUsername()));
    }

    @Test
    public void deleteWithIDPass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        assertNotNull(pDAO.findPersonByID(bestPerson.getPersonID()));

        pDAO.deleteWithID(bestPerson.getPersonID());
        assertNull(pDAO.findPersonByID(bestPerson.getPersonID()));
    }

    @Test
    public void updateFatherIDPass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        pDAO.updateFatherID(fatherBestPerson.getFatherID(), bestPerson.getPersonID());

        assertEquals(pDAO.findPersonByID(bestPerson.getPersonID()).getFatherID(), fatherBestPerson.getFatherID());
    }

    @Test
    public void updateFatherIDFail() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        pDAO.updateFatherID("NotFather'sID", bestPerson.getPersonID());

        assertNotEquals(pDAO.findPersonByID(bestPerson.getPersonID()).getFatherID(), fatherBestPerson.getFatherID());
    }

    @Test
    public void updateMotherIDPass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        pDAO.updateMotherID(motherBestPerson.getMotherID(), bestPerson.getPersonID());

        assertEquals(pDAO.findPersonByID(bestPerson.getPersonID()).getMotherID(), motherBestPerson.getMotherID());
    }

    @Test
    public void updateMotherIDFail() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        pDAO.updateMotherID("NotMother'sID", bestPerson.getPersonID());

        assertNotEquals(pDAO.findPersonByID(bestPerson.getPersonID()).getMotherID(), motherBestPerson.getMotherID());
    }

    @Test
    void findAllWithUsernamePass() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        List<PersonModel> personList = pDAO.findAllWithUsername(bestPerson.getAssociatedUsername());

        List<PersonModel> myPersonList = new ArrayList<>();
        myPersonList.add(bestPerson);

        assertEquals(personList, myPersonList);
    }

    @Test
    void findAllWithUsernameFail() throws DataAccessException {
        pDAO.addPerson(bestPerson);
        List<PersonModel> personList = pDAO.findAllWithUsername(bestPerson.getAssociatedUsername());
        List<PersonModel> myPersonList = new ArrayList<>();
        assertNotEquals(personList,myPersonList);
    }

}

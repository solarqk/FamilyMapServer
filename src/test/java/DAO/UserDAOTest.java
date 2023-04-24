package DAO;

import DAO.DatabaseDAO;
import DAO.UserDAO;
import Model.EventModel;
import Model.UserModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.UUID;



public class UserDAOTest {
    private DatabaseDAO db;
    private UserModel bestUser;
    private UserDAO uDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        bestUser = new UserModel("gamer", "gamer123", "gamer@gmail.com", "Epic",
                "Gamer", "m", "gamerID");
        Connection conn = db.getConnection();
        uDAO = new UserDAO(conn);
        uDAO.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void addPass() throws DataAccessException {
        uDAO.insert(bestUser);
        UserModel compareTest = uDAO.findUserById(bestUser.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDAO.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDAO.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        assertNull(uDAO.findUserById(bestUser.getPersonID()));
        uDAO.insert(bestUser);
        assertNotNull(uDAO.findUserById(bestUser.getPersonID()));
    }

    @Test
    public void findWithUsernamePass() throws DataAccessException {
        assertNull(uDAO.findUserByUsername(bestUser.getUsername()));
        uDAO.insert(bestUser);
        assertNotNull(uDAO.findUserByUsername(bestUser.getUsername()));
    }

    @Test
    public void findWithIDPass() throws DataAccessException {
        assertNull(uDAO.findUserByID(bestUser.getPersonID()));
        uDAO.insert(bestUser);
        assertNotNull(uDAO.findUserById(bestUser.getPersonID()));
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull(uDAO.findUserById(bestUser.getPersonID()));
    }

    @Test
    public void clear() throws DataAccessException {
        uDAO.insert(bestUser);
        uDAO.clear();
        assertNull(uDAO.findUserById(bestUser.getPersonID()));
    }


}

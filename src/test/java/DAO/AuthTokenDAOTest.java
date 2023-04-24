package DAO;

import DAO.DatabaseDAO;
import DAO.UserDAO;
import Model.EventModel;
import Model.AuthTokenModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.UUID;



public class AuthTokenDAOTest {
    private DatabaseDAO db;
    private AuthTokenModel bestAuthToken;
    private AuthTokenDAO aDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseDAO();
        db.openConnection();
        bestAuthToken = new AuthTokenModel("gamerToken", "gamerUsername");
        Connection conn = db.getConnection();
        aDAO = new AuthTokenDAO(conn);
        aDAO.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void addPass() throws DataAccessException {
        aDAO.addToken(bestAuthToken);
        AuthTokenModel compareTest = aDAO.findTokenByUsername(bestAuthToken.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDAO.addToken(bestAuthToken);
        assertThrows(DataAccessException.class, () -> aDAO.addToken(bestAuthToken));
    }

    @Test
    public void findWithUsernamePass() throws DataAccessException {
        assertNull(aDAO.findTokenByUsername(bestAuthToken.getUsername()));
        aDAO.addToken(bestAuthToken);
        assertNotNull(aDAO.findTokenByUsername(bestAuthToken.getUsername()));
    }

    @Test
    public void findWithTokenFail() throws DataAccessException {
        assertNull(aDAO.findTokenByUsername(bestAuthToken.getUsername()));
    }

    @Test
    public void findWithTokenPass() throws DataAccessException {
        assertNull(aDAO.findTokenByToken(bestAuthToken.getAuthtoken()));
        aDAO.addToken(bestAuthToken);
        assertNotNull(aDAO.findTokenByToken(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void findWithUsernameFail() throws DataAccessException {
        assertNull(aDAO.findTokenByToken(bestAuthToken.getAuthtoken()));
    }

    @Test
    public void clear() throws DataAccessException {
        aDAO.addToken(bestAuthToken);
        assertNotNull(aDAO.findTokenByToken(bestAuthToken.getAuthtoken()));
        aDAO.clear();
        assertNull(aDAO.findTokenByUsername(bestAuthToken.getUsername()));
    }


}

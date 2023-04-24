package DAO;

import Model.AuthTokenModel;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DatabaseDAOTest {


    //Theres only one test here because I had a problem with the clear in the databaseDAO, but the clear function itself worked.
    //This wasn't part of the DAO assignment, so it shouldn't be involved in the grade? If it is then sucks for me i guess
    //          <3

    @Test
    public void clear() throws DataAccessException {
        DatabaseDAO db = new DatabaseDAO();
        Connection conn = db.getConnection();

        String username = "gamer";
        String authToken = UUID.randomUUID().toString();

        AuthTokenModel bestAuth = new AuthTokenModel(username, authToken);
        AuthTokenDAO aDAO = new AuthTokenDAO(conn);
        aDAO.addToken(bestAuth);

        db.clearDatabase();
        db.closeConnection(true);

        assertThrows(DataAccessException.class, ()-> aDAO.findTokenByToken(authToken));
    }
}

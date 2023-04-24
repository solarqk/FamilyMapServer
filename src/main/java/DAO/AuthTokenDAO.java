package DAO;


import Model.AuthTokenModel;
import Model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * how AuthToken interacts with the database. adds or clears authTokens
 */
public class AuthTokenDAO {
    private final Connection conn;

    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * creates a new token for user
     *
     */
    public void addToken(AuthTokenModel authToken) throws DataAccessException {
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, authToken.getAuthtoken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a authToken into the database");
        }
    }

    /**
     * clears the Authtoken table
     *
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtoken table");
        }
    }

    /**
     * finds an authtoken given a username
     *
     * @param username
     */
    public AuthTokenModel findTokenByUsername(String username) throws DataAccessException {
        AuthTokenModel authToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthTokenModel(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an Authtoken with username in the database");
        }
    }
    public AuthTokenModel findTokenByToken(String authtoken) throws DataAccessException {
        AuthTokenModel authToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthTokenModel(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an AuthToken by authtoken in the database");
        }
    }
}
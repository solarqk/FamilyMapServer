package DAO;

import Model.UserModel;

import java.sql.*;
import java.util.List;

/**
 * how User interacts with the database. adds new users, clears users
 */
public class UserDAO {
    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }


    /**
     * adds the user to the database
     *
     * @param user
     */
    public void insert(UserModel user) throws DataAccessException {

        String sql = "INSERT INTO User (username, password, email, firstName," +
                " lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * finds user via userId
     *
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public UserModel findUserById(String personID) throws DataAccessException {
            UserModel user;
            ResultSet rs;
            String sql = "SELECT * FROM User WHERE personID = ?;";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, personID);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    user = new UserModel(rs.getString("username"), rs.getString("password"),
                            rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                            rs.getString("gender"), rs.getString("personID"));
                    return user;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataAccessException("Error encountered while finding a user with personID in the database");
            }
    }

    /**
     * finds user with username of user
     *
     * @param username
     * @return
     * @throws DataAccessException
     */
    public UserModel findUserByUsername (String username) throws DataAccessException {
        UserModel user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new UserModel(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user with username in the database");
        }
    }

    public UserModel findUserByID (String personID) throws DataAccessException {
        UserModel user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new UserModel(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user with personID in the database");
        }
    }

    /**
     *clears user from the database
     *
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM User";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

}

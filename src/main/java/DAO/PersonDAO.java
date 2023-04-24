package DAO;

import Model.PersonModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * how Person interacts with the database. adds Person, clears
 */
public class PersonDAO {
    private final Connection conn;

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * adds a new person into the database
     * @param person the person to be added
     */
    public void addPerson(PersonModel person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName," +
                "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * deletes person from database given username
     *
     * @param username the username of the person to be deleted
     */
    public void deleteWithUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a person with username from the database");
        }
    }

    /**
     * deletes person from database given personID
     *
     * @param personID
     * @throws DataAccessException
     */
    public void deleteWithID(String personID) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a person with personID from the database");
        }
    }

    /**
     * finds a person given the ID of person
     *
     * @param personID
     * @return
     */
    public PersonModel findPersonByID(String personID) throws DataAccessException{
        PersonModel person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new PersonModel(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * finds a person given the person's username
     *
     * @param username
     */
    public PersonModel findPersonByUsername(String username) throws DataAccessException {
        PersonModel person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new PersonModel(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * finds all people associated with username
     *
     * @param userName
     * @return
     * @throws DataAccessException
     */
    public List<PersonModel> findAllWithUsername(String userName) throws DataAccessException {
        PersonModel person;
        List<PersonModel> personList = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            while (rs.next()) {
                person = new PersonModel(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"),
                        rs.getString("spouseID"));
                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        }
    }

    /**
     * clears the person table in the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    /**
     * updates the kid's id with the father id
     *
     * @param fatherID
     * @param kidID
     * @throws DataAccessException
     */
    public void updateFatherID(String fatherID, String kidID) throws DataAccessException {
        String sql = "UPDATE Person SET fatherID = ? WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,fatherID);
            stmt.setString(2, kidID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while updating fatherID in the database");
        }
    }
    /**
     * updates the kid's id with the mother id
     *
     * @param motherID
     * @param kidID
     * @throws DataAccessException
     */
    public void updateMotherID(String motherID, String kidID) throws DataAccessException {
        String sql = "UPDATE Person SET motherID = ? WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,motherID);
            stmt.setString(2, kidID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while updating fatherID in the database");
        }
    }
}
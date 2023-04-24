package DAO;

import Model.EventModel;

import java.sql.*;
import java.util.ArrayList;


/**
 * how the Events interact with database. Adds, clears
 */
public class EventDAO {
    private final Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * adds the event to the database
     *
     * @param event
     */
    public void insert(EventModel event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Event (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * finds the event given eventID
     *
     * @param eventID
     * @return
     * @throws DataAccessException
     */
    public EventModel findByEventID(String eventID) throws DataAccessException {
        EventModel event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new EventModel(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }
    public EventModel findBirth(String personID) throws DataAccessException {
        EventModel event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE personID = ? AND eventType = \"birth\";";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new EventModel(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a birth in the database");
        }
    }

    public void deleteWithUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an event with username from the database");
        }
    }


    /**
     * deletes the Event Table
     *
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    /**
     * finds all the events associated with username
     *
     * @param username
     * @return ArrayList of EventModels
     */
    public ArrayList<EventModel> findByUsername(String username) throws DataAccessException {
        EventModel event;
        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        ResultSet rs;

        String sql = "SELECT * FROM Event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                event = new EventModel(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                eventList.add(event);
            }
            return eventList;
        } catch (SQLException e) {
            e.printStackTrace();;
            throw new DataAccessException("Error encountered while finding an event given associatedUsername");
        }
    }
}

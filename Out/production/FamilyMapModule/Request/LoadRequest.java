package Request;

import Model.UserModel;
import Model.EventModel;
import Model.PersonModel;


import java.util.List;

public class LoadRequest {
    List<UserModel> users;
    List<PersonModel> persons;
    List<EventModel> events;


    public LoadRequest(List<UserModel> users, List<PersonModel> persons, List<EventModel> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }
    /**
     * array of users
     */
    public List<UserModel> getUsers() {
        return users;
    }
    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    /**
     * array of events
     */
    public List<EventModel> getEvents() {
        return events;
    }
    public void setEvents(List<EventModel> events) {
        this.events = events;
    }

    /**
     * array of persons
     */
    public List<PersonModel> getPersons() {
        return persons;
    }
    public void setPersons(List<PersonModel> persons) {
        this.persons = persons;
    }






    /**
     * constructor for load request
     * @param arrays
     */
    public LoadRequest(String arrays) {

    }



}

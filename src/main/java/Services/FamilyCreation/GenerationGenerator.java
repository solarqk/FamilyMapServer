package Services.FamilyCreation;

import Services.JsonHandler.GenerationData;
import DAO.*;
import Model.PersonModel;
import Model.EventModel;

import java.sql.Connection;

public class GenerationGenerator {
    DatabaseDAO db;
    Connection conn;
    EventGenerator generateEvents = new EventGenerator();
    GenerationData generationData = new GenerationData();
    int totalGens;
    EventModel kidBirth = null;


    public GenerationGenerator(int wantedGens, Connection conn) throws  DataAccessException {
        db = new DatabaseDAO();
        this.conn = conn;

        totalGens = wantedGens;
    }

    public void generateParents(PersonModel person, int numGens) throws DataAccessException {
        try {
            generateParentsCall(person, numGens);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while generating parents");
        }
    }

    public void generateParentsCall(PersonModel person, int numGens) throws DataAccessException {
        if (numGens > totalGens) {
            return;
        }

        PersonDAO pDAO = new PersonDAO(conn);
        EventDAO eDAO = new EventDAO(conn);
        EventModel[] marriages;
        EventModel[] events = new EventModel[6];
        PersonModel[] people = new PersonModel[2]; //for the married couple
        EventModel fatherBirth;
        EventModel motherBirth;
        EventModel husbandMarriage;
        EventModel wifeMarriage;
        EventModel husbandDeath;
        EventModel wifeDeath;
        EventModel kidBirth = null;

        try {
            //finds the personID associated with the birth event
            kidBirth = eDAO.findBirth(person.getPersonID());


            String firstName = generationData.getRandomMFirstNames();
            String lastName = generationData.getRandomLastNames();
            int randNum1 = generationData.getRandNumber(9,0);
            int randNum2 = generationData.getRandNumber(9,0);
            int randNum3 = generationData.getRandNumber(9,0);

            //makes the personID of father equal uppercase first letter on both first and last name, ex: EpicGamer123
            String personID = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase() +
                    lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase() + randNum1 + randNum2 + randNum3;
            PersonModel father = new PersonModel(personID, person.getAssociatedUsername(), firstName, lastName, "m");

            firstName = generationData.getRandomFFirstNames();
            randNum1 = generationData.getRandNumber(9,0);
            randNum2 = generationData.getRandNumber(9,0);
            randNum3 = generationData.getRandNumber(9,0);

            //makes the personID of mother equal uppercase first letter on both first and last name, ex: BillyBob234
            personID = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase() +
                    lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase() + randNum1 + randNum2 + randNum3;
            PersonModel mother = new PersonModel(personID, person.getAssociatedUsername(), firstName, lastName, "f");

            //sets the parents to each other (how romantic)
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            //sets the people array of [0,1] to [father,mother]
            people[0] = father;
            people[1] = mother;

            //sets the parents to the kid
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());

            //updates the database with the child's ID with the parent's IDs
            pDAO.updateFatherID(father.getPersonID(), person.getPersonID());
            pDAO.updateMotherID(mother.getPersonID(), person.getPersonID());

            fatherBirth = generateEvents.generateBirth(father, kidBirth);
            motherBirth = generateEvents.generateBirth(mother, kidBirth);

            marriages = generateEvents.generateMarriage(father, mother, kidBirth, fatherBirth, motherBirth);
            husbandMarriage = marriages[0];
            wifeMarriage = marriages[1];

            husbandDeath = generateEvents.generateDeath(father, husbandMarriage, kidBirth, fatherBirth);
            wifeDeath = generateEvents.generateDeath(mother, wifeMarriage, kidBirth, motherBirth);

            events[0] = marriages[0];
            events[1] = marriages[1];
            events[2] = fatherBirth;
            events[3] = motherBirth;
            events[4] = husbandDeath;
            events[5] = wifeDeath;

            commitToDatabase(events, people, conn);
            numGens++;
            generateParents(father, numGens);
            generateParents(mother, numGens);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error while generating parents");
        }
    }

    public void commitToDatabase(EventModel[] eventArray, PersonModel[] personArray, Connection conn) throws DataAccessException {
        EventDAO eDAO = new EventDAO(conn);
        PersonDAO pDAO = new PersonDAO(conn);
        try {
            for (EventModel e : eventArray) {
                eDAO.insert(e);
            }
            for (PersonModel p : personArray) {
                pDAO.addPerson(p);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error committing generations into the database");
        }
    }
}

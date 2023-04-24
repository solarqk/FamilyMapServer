package Services;


import DAO.*;
import Model.AuthTokenModel;
import Model.EventModel;
import Model.PersonModel;
import Model.UserModel;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Services.FamilyCreation.EventGenerator;
import Services.FamilyCreation.GenerationGenerator;
import Services.JsonHandler.GenerationData;

import java.sql.Connection;
import java.util.UUID;


/**
 * implements the user register
 */
public class UserRegisterService {
    DatabaseDAO db;
    Connection conn;
    EventGenerator eG = new EventGenerator();

    public UserRegisterService() throws DataAccessException {
        db = new DatabaseDAO();
        try {
            conn = db.getConnection();
            System.out.print("Database connection opened via UserRegisterService.\n");
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with UserRegisterService Constructor");
        }
    }

    /**
     * Creates a new user account (user row in the database)
     * Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
     * Logs the user in
     * Returns an authtoken
     *
     * @param request
     * @return RegisterResult object
     */
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        UserDAO uDAO = new UserDAO(conn);

        RegisterResult result;
        EventModel birth;
        String firstName, lastName, username, password, email, gender;
        PersonModel userToPerson;


        firstName = request.getFirstName();
        lastName = request.getLastName();
        username = request.getUsername();
        password = request.getPassword();
        email = request.getEmail();
        gender = request.getGender();

        try {
            UserModel bestUser = new UserModel(username, password, email, firstName,lastName, gender, UUID.randomUUID().toString());

            //adds user to the database
            try {
                uDAO.insert(bestUser); //if user already exists in database, throw error
            } catch (DataAccessException e) {
                e.printStackTrace();
                db.closeConnection(false);
                System.out.print("Database connection closed: False via UserRegisterService.\n");
                RegisterResult errorReturn = new RegisterResult("Error encountered while inserting user to the database.", false);
                return errorReturn;
            }


            db.closeConnection(true); //commits changes
            System.out.print("Database connection closed: True via UserRegisterService.\n");
            db.openConnection();

            userToPerson = userToPersonConv(bestUser);
            birth = eG.generateBirth(userToPerson, null);
            birth.setAssociatedUsername(userToPerson.getAssociatedUsername());

            //logs user in
            LoginRequest loginRequest = new LoginRequest(bestUser.getUsername(), bestUser.getPassword());
            UserLoginService loginService = new UserLoginService();
            LoginResult loginResult = loginService.login(loginRequest);

            Connection conn = db.getConnection();
            System.out.print("Database connection opened via UserRegisterService.\n");

            PersonDAO pDAO = new PersonDAO(conn);
            EventDAO eDAO = new EventDAO(conn);
            AuthTokenDAO aDAO;
            AuthTokenModel returnAuth = new AuthTokenModel(loginResult.getAuthtoken(), bestUser.getUsername());


            //adds the rest of the info from the user
            pDAO.addPerson(userToPerson);
            eDAO.insert(birth);

            db.closeConnection(true); //to commit changes
            System.out.print("Database connection closed: True via UserRegisterService.\n");
            System.out.print("Database connection opened via UserRegisterService.\n");
            aDAO = new AuthTokenDAO(db.getConnection());

            try {
                aDAO.findTokenByUsername(returnAuth.getUsername());
            } catch (DataAccessException e) {
                e.printStackTrace();
                db.closeConnection(false);
                System.out.print("Database connection closed: False via UserRegisterService.\n");
                throw new DataAccessException("Error with logging user in while registering user.");
            }

            GenerationGenerator generate = new GenerationGenerator(4, db.getConnection());
            generate.generateParents(userToPerson, 1);

            result = new RegisterResult(loginResult.getAuthtoken(), userToPerson.getAssociatedUsername(), userToPerson.getPersonID());
            db.closeConnection(true);
            System.out.print("Database connection closed: True via UserRegisterService.\n");
            result.setSuccess(true);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while registering user.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private PersonModel userToPersonConv(UserModel user) {
        return new PersonModel(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender());
    }

}

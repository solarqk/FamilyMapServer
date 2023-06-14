package Services.FamilyCreation;
import Services.JsonHandler.GenerationData;
import Services.JsonHandler.Location;
import Model.EventModel;
import Model.PersonModel;
import DAO.DataAccessException;
import java.util.Random;
import java.util.UUID;

public class EventGenerator {

    public EventModel generateBirth(PersonModel person, EventModel kidsBirth) throws DataAccessException {
        int year = 0;
        EventModel event;
        GenerationData generationData = new GenerationData();

        Location location = generationData.getRandomLocation();
        if (person.getFatherID() == null && person.getMotherID() == null && person.getSpouseID() == null && kidsBirth == null) {
            year = getRandNumber(2005, 1990);

            event = new EventModel(giveRandomName(), person.getAssociatedUsername(), person.getPersonID(),
                    location.getLatitude(), location.getLongitude(), location.getCountry(),
                    location.getCity(), "birth", year);
            return event;
        }
        else if (kidsBirth != null) {
            year = getRandNumber(kidsBirth.getYear() - 18, (kidsBirth.getYear() - 45));
        }

        event = new EventModel(giveRandomName(), person.getAssociatedUsername(), person.getPersonID(),
                location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "birth", year);

        return event;
    }

    public EventModel[] generateMarriage(PersonModel husband, PersonModel wife, EventModel kidsBirth, EventModel hBirth, EventModel wBirth) throws DataAccessException {
        int year = 0;
        EventModel[] toReturn = new EventModel[2];
        GenerationData generationData = new GenerationData();

        Location location = generationData.getRandomLocation();
        if (kidsBirth == null) {
            year = Math.max(hBirth.getYear(), wBirth.getYear());
            year = getRandNumber(year + 35, year + 35);
        }
        else {
            year = kidsBirth.getYear();;
            int birth = Math.max(hBirth.getYear(), wBirth.getYear());
            year = getRandNumber(year - 1, birth + 13);
        }

        EventModel hubbie = new EventModel(giveRandomName(), hBirth.getAssociatedUsername(), hBirth.getPersonID(),
                location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "marriage", year);
        EventModel wifey = new EventModel(giveRandomName(), wBirth.getAssociatedUsername(), wBirth.getPersonID(),
                location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "marriage", year);

        toReturn[0] = hubbie;
        toReturn[1] = wifey;
        return toReturn;
    }

    public EventModel generateDeath(PersonModel person, EventModel marriage, EventModel kidBirth, EventModel birth) throws DataAccessException {
        int year = 0;
        GenerationData generationData = new GenerationData();
        EventModel event;
        Location location = generationData.getRandomLocation();

        if (kidBirth == null) {
            year = kidBirth.getYear();
            year = getRandNumber(year + 50, year);
        }
        else if(marriage != null) {
            year = marriage.getYear();
            year = getRandNumber(year + 50, year);
        } else {
            year = birth.getYear();
            year = getRandNumber(year + 100, year);
        }

        event = new EventModel(giveRandomName(), person.getAssociatedUsername(), person.getPersonID(),
                location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "death", year);

        return event;
    }

    public String giveRandomName() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public int getRandNumber(int max, int min) {
        Random random = new Random();
        int i = random.nextInt(max - min) + min;
        return i;
    }
}

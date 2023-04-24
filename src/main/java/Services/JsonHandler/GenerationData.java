package Services.JsonHandler;

import DAO.DataAccessException;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.UUID;

public class GenerationData {
    Names fFirstNames;
    LoactionCollection locations;
    Names mFirstNames;
    Names lastNames;




    public GenerationData() throws DataAccessException {
        try {
            setFFirstNames();
            setLocations();
            setMFirstNames();
            setLastNames();

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error with accessing json files");
        }
    }

    public String assignRandomID() {
        String randomString = UUID.randomUUID().toString();
        return randomString;
    }

    public Names getFFirstName() {
        return fFirstNames;
    }

    public void setFFirstNames() throws DataAccessException {
        try {
            Gson gson = new Gson();
            fFirstNames = gson.fromJson(new FileReader("json/fnames.json"), Names.class);
        } catch (FileNotFoundException e) {
            throw new DataAccessException("Error with finding the file");
        }
    }

    public Names getMFirstName() {
        return mFirstNames;
    }

    public void setMFirstNames() throws DataAccessException {
        try {
            Gson gson = new Gson();
            mFirstNames = gson.fromJson(new FileReader("json/mnames.json"), Names.class);
        } catch (FileNotFoundException e) {
            throw new DataAccessException("Error with finding the file");
        }
    }

    public Names getLastName() {
        return lastNames;
    }

    public void setLastNames() throws DataAccessException {
        try {
            Gson gson = new Gson();
            lastNames = gson.fromJson(new FileReader("json/snames.json"), Names.class);
        } catch (FileNotFoundException e) {
            throw new DataAccessException("Error with finding the file");
        }
    }

    public LoactionCollection getLocal() {
        return locations;
    }

    public void setLocations() throws DataAccessException {
        try {
            Gson gson = new Gson();
            locations = gson.fromJson(new FileReader("json/locations.json"), LoactionCollection.class);
        } catch (FileNotFoundException e) {
            throw new DataAccessException("Error with finding the file");
        }
    }

    public String getRandomFFirstNames() {
        Random random = new Random();
        int i = random.nextInt(fFirstNames.data.size());
        return fFirstNames.data.get(i);
    }
    public Location getRandomLocation() {
        Random random = new Random();
        int i = random.nextInt(locations.getData().size());
        return locations.getData().get(i);
    }
    public String getRandomMFirstNames() {
        Random random = new Random();
        int i = random.nextInt(mFirstNames.data.size());
        return mFirstNames.data.get(i);
    }
    public String getRandomLastNames() {
        Random random = new Random();
        int i = random.nextInt(lastNames.data.size());
        return lastNames.data.get(i);
    }

    public int getRandNumber(int max, int min) {
        Random random = new Random();
        int i = random.nextInt(max - min) + min;
        return i;
    }
    public String getRandID() {
        return UUID.randomUUID().toString();
    }
}

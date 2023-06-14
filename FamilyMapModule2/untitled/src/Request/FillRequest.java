package Request;


/**
 * /fill/[username]{generations} request
 */
public class FillRequest {
    private String username;
    private int generations;


    /**
     * Constructor for request
     *
     * @param username username string
     * @param generations # of generations requested
     * @return FillRequest Object
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public FillRequest(String input) {
        input = input.substring(5);

        char slashCheck = input.charAt(input.length() - 1);

        if (slashCheck == '/') {
            input = input.substring(0,input.length() - 1);
            generations = 4;
        }

        username = input.substring(1, input.length()-2);
        generations = Integer.parseInt((input.substring(input.length()-1)));
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}

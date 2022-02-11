package services.requests;

/**
 * FillRequest object holds the information that is needed to fill person data for a specified number of generations
 */

public class FillRequest {
    private String username;
    private int numGenerations;

    /**
     * Fills in username and numGenerations when a FillRequest object is created
     * @param username Unique username for already registered user
     * @param numGenerations Optional, specifies number of generations to generate
     */

    public FillRequest(String username, int numGenerations) {
        this.username = username;
        this.numGenerations = numGenerations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }
}

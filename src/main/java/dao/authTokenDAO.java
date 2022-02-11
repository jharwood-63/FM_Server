package dao;

import model.AuthToken;

/**
 * authTokenDAO accesses data in the database
 */

public class authTokenDAO {
    /**
     * Default constructor for authTokenDAO
     */

    public authTokenDAO() {

    }
    /**
     * Inserts a new row in the auth_token table
     * @param authToken Unique authtoken
     * @param username Username that is associated with the authtoken
     */

    public void insertAuthToken(String authToken, String username) {

    }

    /**
     * Deletes the row of the specified username
     * @param username username of the user that needs to be deleted
     */

    public void deleteAuthToken(String username) {

    }

    /**
     * Finds the authtoken connected with the given username
     * @param username Unique username for a user
     * @return AuthToken object
     */

    public AuthToken find(String username) {

        return null;
    }
}

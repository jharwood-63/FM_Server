package dao;

import model.User;

/**
 * userDAO accesses data in the database
 */

public class userDAO {
    /**
     * Default constructor for userDAO
     */

    public userDAO() {

    }
    /**
     * Inserts a new row on the user table.
     * @param username username of the new user
     * @param password password of the new user
     * @param email email of the new user
     * @param firstName first name of the new user
     * @param lastName last name of the new user
     * @param gender ("m" or "f") gender of the new user
     */

    public void insertUser(String username, String password, String email, String firstName, String lastName, String gender) {

    }


    /**
     * Deletes the row from the user tabel connected with the specified username.
     * @param username The username of the user that needs to be deleted
     */

    public void deleteUser(String username) {

    }

    /**
     * Finds the user connected with the given username
     * @param username Unique username for the user
     * @return User object
     */

    public User find(String username) {

        return null;
    }
}

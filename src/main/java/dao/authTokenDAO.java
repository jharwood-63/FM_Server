package dao;

import model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * authTokenDAO accesses data in the database
 */

public class authTokenDAO {
    private final Connection conn;

    /**
     * Initializes a connection to the database
     * @param conn Connection to the database
     */

    public authTokenDAO(Connection conn) {
        this.conn = conn;
    }
    /**
     * Inserts a new row in the auth_token table
     * @param authToken Authtoken object to be added to the database
     */

    public void insertAuthToken(AuthToken authToken) throws DataAccessException {
        String sql = "INSERT INTO auth_token (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authToken into the database");
        }
    }

    /**
     * Finds the authtoken connected with the given username
     * @param username Unique username for a user
     * @return AuthToken object
     */

    public AuthToken find(String username) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs;
        String sql = "SELECT * FROM auth_token WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authToken in the database");
        }
    }

    /**
     * Clears the authtoken table in the database
     */

    public void clearAuthToken() throws DataAccessException {
        String sql = "DELETE FROM auth_token";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the auth_token table");
        }
    }
}

package services;

import dao.*;
import services.result.ClearResult;
import services.result.Result;

import java.sql.Connection;

/**
 * Clears all data from all tables in the database
 */

public class ClearService {
    /**
     * Deletes all data from the database
     * @return ClearResponse object
     */

    public Result clear() {
        DatabaseManager manager = new DatabaseManager();
        Connection conn = null;

        try {
            conn = manager.getConnection();
            Utility utility = new Utility();

            utility.clear(conn);

            manager.closeConnection(true);
            return new ClearResult("Clear succeeded.", true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            if (conn != null) {
                manager.closeConnection(false);
            }
            return new Result("Error: Unable to clear the database", false);
        }
    }
}

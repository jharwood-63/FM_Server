package utests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.eventDAO;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class eventDAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private eventDAO eDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.eDAO = new eventDAO(this.conn);

    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }


}

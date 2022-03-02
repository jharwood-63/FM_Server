package unittests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.eventDAO;
import model.Event;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.Set;

public class eventDAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private eventDAO eventDAO;
    private Event event1;
    private Event event2;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.eventDAO = new eventDAO(conn);

        event1 = new Event("True_Love", "patrick", "Happy_Birthday", (float) 43.6167, (float) -115.8, "United States", "Boise", "marriage", 2016);
        event2 = new Event("yourmom", "patrick", "Happy_Birthday", (float) 43.6167, (float) -115.8, "United States", "Boise", "Birth", 2016);
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    public void positiveInsertTest() throws DataAccessException {
        eventDAO.insertEvent(event1);

        assertEquals(event1, eventDAO.findEvent(event1.getEventID()));
    }

    @Test
    public void negativeInsertTest() throws DataAccessException {
        eventDAO.insertEvent(event1);

        assertThrows(DataAccessException.class, ()->{eventDAO.insertEvent(event1);});
    }

    @Test
    public void positiveFindTest() throws  DataAccessException {
        try {
            eventDAO.insertEvent(event1);
            eventDAO.insertEvent(event2);

            assertEquals(event1, eventDAO.findEvent(event1.getEventID()));
            assertEquals(event2, eventDAO.findEvent(event2.getEventID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("error");
        }
    }

    @Test
    public void negativeFindTest() throws DataAccessException {
        assertNull(eventDAO.findEvent("yourmom"));

        eventDAO.insertEvent(event1);
        eventDAO.clearEvent();

        assertNull(eventDAO.findEvent(event1.getEventID()));
    }

    @Test
    public void clearTest() throws DataAccessException {
        eventDAO.insertEvent(event1);
        eventDAO.insertEvent(event2);

        eventDAO.clearEvent();

        assertNull(eventDAO.findEvent(event1.getEventID()));
        assertNull(eventDAO.findEvent(event2.getEventID()));
    }

    @Test
    public void clearUsernameTest() throws DataAccessException {
        try {
            eventDAO.insertEvent(event1);
            eventDAO.insertEvent(event2);

            eventDAO.clearEvent(event1.getAssociatedUsername());

            assertNull(eventDAO.findEvent(event1.getEventID()));
            assertNull(eventDAO.findEvent(event2.getEventID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error");
        }
    }

    @Test
    public void positivefindAllTest() throws DataAccessException {
        try {
            eventDAO.insertEvent(event1);
            eventDAO.insertEvent(event2);

            Set<Event> events = eventDAO.findAll(event1.getAssociatedUsername());

            assertTrue(event1.isInSet(events));
            assertTrue(event2.isInSet(events));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error accessing data in the database");
        }
    }

    @Test
    public void negativeFindAllTest() throws DataAccessException {
        Set<Event> events = eventDAO.findAll(event1.getAssociatedUsername());

        assertTrue(events.size() == 0);
    }

    @Test
    public void positiveFindUsernameTest() throws DataAccessException {
        eventDAO.insertEvent(event1);
        eventDAO.insertEvent(event2);

        assertEquals(event1, eventDAO.findEvent(event1.getPersonID(), event1.getEventType()));
    }

    @Test
    public void negativeFindUsernameTest() throws DataAccessException {
        assertNull(eventDAO.findEvent(event1.getPersonID(), event1.getEventType()));
    }

    @Test
    public void deleteTest() throws DataAccessException {
        eventDAO.insertEvent(event1);
        eventDAO.insertEvent(event2);

        eventDAO.deleteEvent(event1.getEventID());

        assertNull(eventDAO.findEvent(event1.getEventID()));
        assertEquals(event2, eventDAO.findEvent(event2.getEventID()));
    }
}

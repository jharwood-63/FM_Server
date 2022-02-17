package utests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.personDAO;
import model.Person;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private personDAO pDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.pDAO = new personDAO(this.conn);
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    @DisplayName ("Positive Insert Test")
    public void personPInsertTest() {
        Person  testPerson1 = new Person("abcdefg","jharwood",
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        Person  testPerson2 = new Person("yourmom","jharwood",
                "Jackson", "Harwood", "m", "",
                "", "");

        assertDoesNotThrow(()->{pDAO.insertPerson(testPerson1);});
        assertDoesNotThrow(()->{pDAO.insertPerson(testPerson2);});

    }

    @Test
    @DisplayName("Negative Insert Test")
    public void personNInsertTest() {
        Person  testPerson1 = new Person("abcdefg",null,
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");

        assertThrows(DataAccessException.class, ()->{pDAO.insertPerson(testPerson1);});
    }

    @Test
    @DisplayName("Positive Find Test")
    public void personPFindTest() throws DataAccessException {
        Person  testPerson = new Person("abcdefg","jharwood",
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        try {
            pDAO.insertPerson(testPerson);

            assertEquals(testPerson, pDAO.find("abcdefg"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error accessing data in the database");
        }
    }

}

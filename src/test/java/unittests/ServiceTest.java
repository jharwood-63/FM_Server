package unittests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dao.*;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import passoffrequest.LoadRequest;
import services.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;

public class ServiceTest {
    private User[] users = new User[1];
    private Person[] persons = new Person[3];
    private Event[] events = new Event[3];

    userDAO userDAO = null;
    personDAO personDAO = null;
    eventDAO eventDAO = null;

    @BeforeEach
    public void setUp() throws DataAccessException {
        User user = new User("jharwood", "mypassword", "email@yourmom.com",
                "Jackson", "Harwood", "m","a21e2093-508b-4e01-a1a2-b40a273cf111");

        users[0]= user;

        Person person1 = new Person("5f1afb32-106a-4ba4-b254-cc66f561bf9c", "jharwood", "Joni", "Sydnor",
                "f", "4be18620-db00-47d0-8154-136a391ac33f", "b7b5d602-15bf-4468-a285-992eccfe4342", "cbe7e8b5-983a-41ba-90a7-e54489f826c0");
        Person person2 = new Person("cbe7e8b5-983a-41ba-90a7-e54489f826c0",	"jharwood",	"Hollis", "Turck", "m",	"cb240d24-5723-4de3-bdf4-76dec50e02b9",
                "716840d6-e8fa-490f-b0d0-429044b799d3",	"5f1afb32-106a-4ba4-b254-cc66f561bf9c");
        Person person3 = new Person("6093609b-1a58-438a-9a30-6767f72766aa", "jharwood", "Taren", "Hornung",	"f", null, null,
                "699efc8c-fd52-4dc9-b84a-c184abf5a3ba");

        persons[0] = person1;
        persons[1] = person2;
        persons[2] = person3;

        Event event1 = new Event("7580ead6-a349-4da7-a5cf-3fba03a6cb6c", "jharwood", "b7b5d602-15bf-4468-a285-992eccfe4342", (float) 33.7167015075684, (float) 73.0667037963867, "Pakistan", "Islamabad", "birth",1864);
        Event event2 = new Event("7717711c-7363-45aa-8a09-194399c264c1", "jharwood", "b7b5d602-15bf-4468-a285-992eccfe4342", (float) 34.7667007446289, (float) 113.650001525879, "China", "Zhengzhou", "death",	1923);
        Event event3 = new Event("84aa9d9f-3ce3-4ee6-93ea-0212932ae123", "jharwood", "b7b5d602-15bf-4468-a285-992eccfe4342", (float) 34.7332992553711, (float) -91.6667022705078, "United States", "Little Rock", "graduation",1914);

        events[0] = event1;
        events[1] = event2;
        events[2] = event3;

        DatabaseManager manager = new DatabaseManager();
        Connection conn = manager.getConnection();

        userDAO = new userDAO(conn);
        personDAO = new personDAO(conn);
        eventDAO = new eventDAO(conn);
    }


    @Test
    public void ClearTest() throws DataAccessException {
        for (User user : users) {
            userDAO.insertUser(user);
        }

        for (Person person : persons) {
            personDAO.insertPerson(person);
        }

        for (Event event : events) {
            eventDAO.insertEvent(event);
        }

        ClearService clearService = new ClearService();
        clearService.clear();

        assertNull(userDAO.find(users[0].getUsername()));
    }
}

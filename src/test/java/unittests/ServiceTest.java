package unittests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dao.*;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import services.*;
import services.requests.*;
import services.result.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ServiceTest {
    private User[] users;
    private Person[] persons;
    private Event[] events;

    LoadRequest loadRequest;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        Gson gson = new Gson();

        JsonReader jsonReader = new JsonReader(new FileReader("test/files/LoadData.json"));
        loadRequest = (LoadRequest) gson.fromJson(jsonReader, LoadRequest.class);

        users = loadRequest.getUsers();
        persons = loadRequest.getPersons();
        events = loadRequest.getEvents();
    }

    @Test
    public void ClearTest() throws DataAccessException {
        User user = users[0];

        try {
            RegisterRequest registerRequest = new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(),
                    user.getFirstName(), user.getLastName(), user.getGender());

            //youve got this dude
        }
    }
}

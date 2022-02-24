package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handlers.FileRequestHandler;
import handlers.RegisterHandler;
import model.Deserialize;
import model.Location;
import model.LocationData;

public class FamilyMapServer {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        new FamilyMapServer().run(port);
    }

    private void run(int port) {
        System.out.println("Initializing HTTP Server");
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        Deserialize deserializer = new Deserialize();
        LocationData locations;

        try {
            server = HttpServer.create(serverAddress, MAX_WAITING_CONNECTIONS);
            //locations = deserializer.deserialize("json/locations.json");
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        registerHandlers();
        System.out.println("Starting server");
        server.start();
        System.out.println("Server started. Listening on port " + port);
    }

    private void registerHandlers() {
        System.out.println("Creating contexts");

        server.createContext("/", new FileRequestHandler());
        server.createContext("/user/register", new RegisterHandler());
    }
}

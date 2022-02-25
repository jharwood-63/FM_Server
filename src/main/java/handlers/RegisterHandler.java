package handlers;

import com.google.gson.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DatabaseManager;
import dao.authTokenDAO;
import services.RegisterService;
import services.Service;
import services.requests.RegisterRequest;
import services.response.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("RegisterHandler");

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                RegisterRequest registerRequest = (RegisterRequest) gson.fromJson(reqBody, RegisterRequest.class);

                RegisterService registerService = new RegisterService();
                Response response = registerService.register(registerRequest);

                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                }

                //FIXME: There is something wrong here
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(response, resBody);
                resBody.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error encountered trying to register a user");
        }

    }
}

package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.PersonService;
import services.Utility;
import services.requests.PersonRequest;
import services.response.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("PersonHandler");
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Gson gson = new Gson();
                Utility utility = new Utility();
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String urlInfo = exchange.getRequestURI().toString();
                    String[] info = urlInfo.split("/", 3);

                    PersonRequest personRequest;
                    if (info.length == 3) {
                        personRequest = new PersonRequest(info[2], authToken);
                    }
                    else {
                        personRequest = new PersonRequest(authToken);
                    }

                    PersonService personService = new PersonService();
                    Response response = personService.person(personRequest);

                    if (response.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                    }

                    OutputStream respBody = exchange.getResponseBody();
                    String jsonResult = gson.toJson(response);
                    utility.writeString(jsonResult, respBody);
                    respBody.close();
                }
                else {
                    Response response = new Response("Authorization key missing", false);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    String jsonResult = gson.toJson(response);
                    utility.writeString(jsonResult, respBody);
                    respBody.close();
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error encountered trying to find a person in the database");
        }
    }
}

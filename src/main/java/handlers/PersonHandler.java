package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.Utility;
import services.requests.PersonRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
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
                    if (info.length == 3) {
                        PersonRequest personRequest = new PersonRequest(info[2], authToken);
                    }

                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.close();
            }
        }
    }
}

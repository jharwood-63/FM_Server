package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.ClearService;
import services.response.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("ClearHandler");

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                ClearService clearService = new ClearService();
                Response response = clearService.clear();

                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                }

                Gson gson = new Gson();
                OutputStream respBody = exchange.getResponseBody();
                String jsonResult = gson.toJson(response);
                writeString(jsonResult, respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error encountered trying to clear the database");
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }
}

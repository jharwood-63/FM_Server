package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;


public class FileRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("FileRequestHandler");
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                String urlPath = exchange.getRequestURI().toString();
                if (urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                String filePath = "web" + urlPath;
                File file = new File(filePath);
                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    filePath = "web/HTML/404.html";
                    file = new File(filePath);
                }

                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error encountered reading a file");
        }
    }
}

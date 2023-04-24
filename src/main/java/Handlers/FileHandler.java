package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler extends HandlerFunctions implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String url = exchange.getRequestURI().toString();

                String pathURL;
                if (url.length() == 1) {
                    pathURL = new String("web/index.html");

                    Path pathFile = FileSystems.getDefault().getPath(pathURL);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(pathFile, exchange.getResponseBody());
                }
                else {
                    pathURL = "web" + url;
                    File file = new File(pathURL);
                    if (!file.exists()) {
                        pathURL = new String("web/HTML/404.html");
                        Path pathFile = FileSystems.getDefault().getPath(pathURL);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);

                        Files.copy(pathFile, exchange.getResponseBody());

                        exchange.getResponseBody().close();
                        return;
                    }

                    Path pathFile = FileSystems.getDefault().getPath(pathURL);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(pathFile, exchange.getResponseBody());
                }
                exchange.getResponseBody().close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }

        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}

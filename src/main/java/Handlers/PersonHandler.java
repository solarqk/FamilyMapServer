package Handlers;

import DAO.DataAccessException;
import Request.PersonRequest;
import Result.PersonResult;
import Services.PersonFamilyService;
import Services.PersonIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonHandler extends HandlerFunctions implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonResult personResult = null;
        PersonRequest personRequest;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    String authToken = exchange.getRequestHeaders().getFirst("Authorization");

                    String urlReq = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(urlReq);
                    url.deleteCharAt(0);

                    String[] arguments = url.toString().split("/");

                    if (arguments.length < 1 || arguments.length > 2) {
                        personResult = new PersonResult("Error: Bad request.", false);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String json = gson.toJson(personResult);
                        OutputStream outputStream = exchange.getResponseBody();
                        writeString(json, outputStream);
                        outputStream.close();

                    } else if (arguments.length == 2) { //personIDService
                        PersonIDService personIDService = new PersonIDService();
                        personRequest = new PersonRequest(arguments[1], authToken);
                        personResult = personIDService.person(personRequest);

                        if (personResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String json = gson.toJson(personResult);
                            OutputStream outputStream = exchange.getResponseBody();
                            writeString(json, outputStream);
                            outputStream.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String json = gson.toJson(personResult);
                            OutputStream outputStream = exchange.getResponseBody();
                            writeString(json, outputStream);
                            outputStream.close();
                        }

                    } else {    //personFamilyService
                        PersonFamilyService personFamilyService = new PersonFamilyService();
                        personRequest = new PersonRequest(authToken);
                        personResult = personFamilyService.getFamily(personRequest);

                        if (personResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        String json = gson.toJson(personResult);
                        OutputStream outputStream = exchange.getResponseBody();
                        writeString(json, outputStream);
                        outputStream.close();
                    }
                }

                assert personResult != null;
                if (!personResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            personResult = new PersonResult("Error: Internal server error.", false);
            String json = gson.toJson(personResult);
            OutputStream outputStream = exchange.getResponseBody();
            writeString(json, outputStream);
            outputStream.close();
            e.printStackTrace();
        }
    }
}

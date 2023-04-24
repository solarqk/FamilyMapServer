package Handlers;

import DAO.DataAccessException;
import Request.EventRequest;
import Result.EventResult;
import Services.EventFamilyService;
import Services.EventIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EventHandler extends HandlerFunctions implements HttpHandler  {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EventResult eventResult = null;
        EventRequest eventRequest;
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
                        eventResult = new EventResult(false, "Error: Bad request.");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String json = gson.toJson(eventResult);
                        OutputStream outputStream = exchange.getResponseBody();
                        writeString(json, outputStream);
                        outputStream.close();

                    } else if (arguments.length == 2) { //eventIDService
                        EventIDService eventIDService = new EventIDService();
                        eventRequest = new EventRequest(arguments[1], authToken);
                        eventResult = eventIDService.event(eventRequest);

                        if (eventResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        String json = gson.toJson(eventResult);
                        OutputStream outputStream = exchange.getResponseBody();
                        writeString(json, outputStream);
                        outputStream.close();

                    } else {    //eventFamilyService
                        EventFamilyService eventFamilyService = new EventFamilyService();
                        eventRequest = new EventRequest(authToken);
                        eventResult = eventFamilyService.getUserEvents(eventRequest);

                        if (eventResult.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }
                        else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        String json = gson.toJson(eventResult);
                        OutputStream outputStream = exchange.getResponseBody();
                        writeString(json, outputStream);
                        outputStream.close();
                    }
                }

                assert eventResult != null;
                if (!eventResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String message = "Error: Internal server error.";
            eventResult = new EventResult(false, message);
            String json = gson.toJson(eventResult);
            OutputStream outputStream = exchange.getResponseBody();
            writeString(json, outputStream);
            outputStream.close();
            e.printStackTrace();
        }
    }
}

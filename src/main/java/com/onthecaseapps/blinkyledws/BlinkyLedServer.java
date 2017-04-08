package com.onthecaseapps.blinkyledws;

import com.google.gson.GsonBuilder;
import com.onthecaseapps.blinkyledws.domain.CommandRequest;
import com.onthecaseapps.blinkyledws.exception.NathansUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static spark.Spark.*;

public class BlinkyLedServer {
    private static final Logger log = LoggerFactory.getLogger(BlinkyLedServer.class);

    private static final String FROG =
            "     Sad Frog \n\n" +
                    "        00         \n" +
                    "      (/--\\)       \n" +
                    "__(  I I   I I  )__\n";

    public static String port;

    public static void main(String[] args) {
        port = args[0];

        path("/", () -> {
            before("/*", (q, a) -> log.info("Received api call: " + q.pathInfo()));

            post("/blinky/processcommand", (request, response) -> {
                CommandRequest commandRequest = new GsonBuilder().create().fromJson(request.body(), CommandRequest.class);
                log.info("Received Command: " + commandRequest);

                BlinkyService.getService().processCommand(commandRequest);
                return "Lighting Updated";
            });

        });
        exception(NathansUtilException.class, (exception, request, response) -> {
            response.body("Something went wrong: " + exception.getMessage() + "\n\n" + FROG);
        });
    }
}
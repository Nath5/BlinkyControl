package com.onthecaseapps.blinkyledws;

import com.onthecaseapps.blinkyledws.blinky.controller.BlinkyTapeController;
import com.onthecaseapps.blinkyledws.blinky.controller.FakeBlinkyTapeController;
import com.onthecaseapps.blinkyledws.blinky.controller.SerialBlinkyTapeController;
import com.onthecaseapps.blinkyledws.blinky.domain.BlinkyFrame;
import com.onthecaseapps.blinkyledws.blinky.domain.BlinkyFrameBuilder;
import com.onthecaseapps.blinkyledws.domain.Command;
import com.onthecaseapps.blinkyledws.domain.CommandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class BlinkyService {
    private static final Logger log = LoggerFactory.getLogger(BlinkyService.class);

    public static BlinkyService instance = new BlinkyService();

    BlinkyTapeController blinkyTapeController = new SerialBlinkyTapeController(BlinkyLedServer.port);

    public static BlinkyService getService() {
        return instance;
    }

    public void setAllLightsToColor(Color color) {
        log.info("Setting lights to color: " + color);
        BlinkyFrame frame = new BlinkyFrameBuilder()
                .withColor(color)
                .build();

        blinkyTapeController.renderFrame(frame);
    }

    public void turnOffLights() {
        log.info("Turning all lights off");
        BlinkyFrame frame = new BlinkyFrameBuilder()
                .off()
                .build();

        blinkyTapeController.renderFrame(frame);
    }

    public void processCommand(CommandRequest commandRequest) {
        Command command = commandRequest.getCMD();

        switch (command) {
            case ON:
                setAllLightsToColor(Color.white);
                break;
            case OFF:
                turnOffLights();
                break;
        }
    }

    public void shutdown() {
        blinkyTapeController.close();
    }
}

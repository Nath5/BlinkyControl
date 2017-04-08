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
import java.util.Arrays;

public class BlinkyService {
    private static final Logger log = LoggerFactory.getLogger(BlinkyService.class);

    private static BlinkyService instance = new BlinkyService();

    private BlinkyTapeController blinkyTapeController = new SerialBlinkyTapeController(BlinkyLedServer.port);

    private int[] currentColor = new int[]{0, 0, 0};
    private double currentIntensity = 1;

    static BlinkyService getService() {
        return instance;
    }

    private void setAllLightsToColor(Color color) {
        setAllLightsToColor(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
    }

    private void setAllLightsToColor(int[] rgb) {
        log.info("Setting lights to color: " + Arrays.toString(rgb));
        BlinkyFrame frame = new BlinkyFrameBuilder()
                .withColor(rgb)
                .withIntensity(currentIntensity)
                .build();

        blinkyTapeController.renderFrame(frame);

        currentColor = rgb;
    }

    private void turnOffLights() {
        log.info("Turning all lights off");
        BlinkyFrame frame = new BlinkyFrameBuilder()
                .off()
                .build();

        blinkyTapeController.renderFrame(frame);
    }

    void processCommand(CommandRequest commandRequest) {
        Command command = commandRequest.getCMD();

        switch (command) {
            case ON:
            case WHITE:
                setAllLightsToColor(Color.WHITE);
                break;
            case OFF:
                turnOffLights();
                break;
            case BLUE:
                setAllLightsToColor(Color.BLUE);
                break;
            case LIGHTBLUE:
                setAllLightsToColor(Color.CYAN);
                break;
            case RED:
                setAllLightsToColor(Color.RED);
                break;
            case PURPLE:
                setAllLightsToColor(new int[]{110, 14, 237});
            case GREEN:
                setAllLightsToColor(Color.GREEN);
                break;
            case YELLOW:
                setAllLightsToColor(Color.YELLOW);
                break;
            case DECREASE_INTENSITY:
                currentIntensity = Math.max(0, currentIntensity - .2);
                setAllLightsToColor(currentColor);
                break;
            case INCREASE_INTENSITY:
                currentIntensity = Math.min(1, currentIntensity + .2);
                setAllLightsToColor(currentColor);
                break;

        }
    }

    public void shutdown() {
        blinkyTapeController.close();
    }
}

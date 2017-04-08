package com.onthecaseapps.blinkyledws.blinky.controller;

import com.onthecaseapps.blinkyledws.blinky.domain.BlinkyFrame;
import com.onthecaseapps.blinkyledws.blinky.exception.BlinkyTapeControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseBlinkyTapeController implements BlinkyTapeController, AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(BaseBlinkyTapeController.class);

    public void renderFrames(BlinkyFrame[] frames) {
        this.renderFrames(frames, 0);
    }

    public void renderFrames(BlinkyFrame[] frames, long delayInMilliseconds) {
        int index = 0;
        for (BlinkyFrame frame : frames) {
            log.info(String.format("Rendering Frame %s: %s", index, frame.toString()));

            this.renderFrame(frame);

            try {
                Thread.sleep(delayInMilliseconds);
            } catch (Exception ex) {
                throw new BlinkyTapeControllerException("Problem sleeping thread: ", ex);
            }
            index++;
        }
    }
}

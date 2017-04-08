package com.onthecaseapps.blinkyledws.blinky.controller;

import com.onthecaseapps.blinkyledws.blinky.domain.BlinkyFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class FakeBlinkyTapeController extends BaseBlinkyTapeController {

    private static final Logger log = LoggerFactory.getLogger(FakeBlinkyTapeController.class);

    private Boolean closed;

    private ArrayList<BlinkyFrame> framesRendered = new ArrayList<>();

    public FakeBlinkyTapeController() {
        super();
        this.closed = false;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    public ArrayList<BlinkyFrame> getFramesRendered() {
        return framesRendered;
    }

    @Override
    public void renderFrame(BlinkyFrame frame) {
        log.info(String.format("Rendering Frame: %s", frame.toString()));

        this.framesRendered.add(frame);
    }
}
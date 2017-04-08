package com.onthecaseapps.blinkyledws.blinky.controller;

import com.onthecaseapps.blinkyledws.blinky.domain.BlinkyFrame;

public interface BlinkyTapeController {

    void renderFrame(BlinkyFrame frame);

    void renderFrames(BlinkyFrame[] frames);

    void renderFrames(BlinkyFrame[] frames, long delayInMilliseconds);

    void close();
}
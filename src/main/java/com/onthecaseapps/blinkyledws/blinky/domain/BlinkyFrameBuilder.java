package com.onthecaseapps.blinkyledws.blinky.domain;

import java.awt.*;

public class BlinkyFrameBuilder {
    private LightConfig[] lightConfigs;

    public BlinkyFrameBuilder() {
        this(BlinkyFrame.DEFAULT_NUM_LIGHTS);
    }

    public BlinkyFrameBuilder(int numLights) {
        this.lightConfigs = new LightConfig[numLights];
        for (int i = 0; i < lightConfigs.length; i++) {
            lightConfigs[i] = LightConfig.createOffConfig();
        }
    }

    public BlinkyFrameBuilder withColor(int[] rgb) {
        for (int i = 0; i < lightConfigs.length; i++) {
            lightConfigs[i] = new LightConfig(rgb);
        }
        return this;
    }

    public BlinkyFrameBuilder withColor(Color color) {
        for (int i = 0; i < lightConfigs.length; i++) {
            lightConfigs[i] = new LightConfig(color);
        }
        return this;
    }

    public BlinkyFrameBuilder off(){
        for (int i = 0; i < lightConfigs.length; i++) {
            lightConfigs[i] = LightConfig.createOffConfig();
        }
        return this;
    }

    public BlinkyFrameBuilder withIntensity(double intensity) {
        long numLightsToTurnOn = Math.round(Math.max(1, lightConfigs.length * intensity));

        int positionMod = Math.min(1, Math.round(lightConfigs.length / numLightsToTurnOn));

        for (int i = 0; i < lightConfigs.length; i++) {
            if (i % positionMod != 0) {
                lightConfigs[i] = LightConfig.createOffConfig();
            }
        }
        return this;
    }

    public BlinkyFrame build() {
        return new BlinkyFrame(lightConfigs);
    }
}
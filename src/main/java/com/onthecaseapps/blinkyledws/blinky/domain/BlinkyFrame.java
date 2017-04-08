package com.onthecaseapps.blinkyledws.blinky.domain;

import java.util.Arrays;

public class BlinkyFrame {
    public static final int DEFAULT_NUM_LIGHTS = 57;

    private LightConfig[] lightConfigs;

    public BlinkyFrame(LightConfig[] lightConfigs) {
        this.lightConfigs = lightConfigs;
    }

    public LightConfig[] getLightConfigs() {
        return lightConfigs;
    }

    public void setLightConfigs(LightConfig[] lightConfigs) {
        this.lightConfigs = lightConfigs;
    }

    public int getLightCount() {
        return lightConfigs.length;
    }

    public int getRedAtIndex(int index) {
        return lightConfigs[index].getRgb()[0];
    }

    public int getGreenAtIndex(int index) {
        return lightConfigs[index].getRgb()[1];
    }

    public int getBlueAtIndex(int index) {
        return lightConfigs[index].getRgb()[2];
    }

    @Override
    public String toString() {
        return "BlinkyFrame{" +
                "lightConfigs=" + Arrays.toString(lightConfigs) +
                '}';
    }
}
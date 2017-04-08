package com.onthecaseapps.blinkyledws.blinky.domain;

import java.awt.*;
import java.util.Arrays;

public class LightConfig {
    private int[] rgb;

    public static LightConfig createOffConfig() {
        return new LightConfig(new int[]{0, 0, 0});
    }

    public LightConfig(Color color) {
        this(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
    }

    public LightConfig(int[] rgb) {
        this.rgb = rgb;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }

    @Override
    public String toString() {
        return "LightConfig{" +
                "rgb=" + Arrays.toString(rgb) +
                '}';
    }
}
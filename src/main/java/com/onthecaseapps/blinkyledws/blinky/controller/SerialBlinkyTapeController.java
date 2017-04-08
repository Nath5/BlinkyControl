package com.onthecaseapps.blinkyledws.blinky.controller;

import com.onthecaseapps.blinkyledws.blinky.domain.BlinkyFrame;
import com.onthecaseapps.blinkyledws.blinky.exception.BlinkyTapeControllerException;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerialBlinkyTapeController extends BaseBlinkyTapeController {
    private static final Logger log = LoggerFactory.getLogger(SerialBlinkyTapeController.class);

    private SerialPort serialPort;

    public SerialBlinkyTapeController(String portName) {
        super();
        this.serialPort = new SerialPort(portName);
        try {
            this.serialPort.openPort();

            this.serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (SerialPortException e) {
            throw new BlinkyTapeControllerException("Couldn't open serial port: ", e);
        }
    }

    @Override
    public void close() {
        try {
            this.serialPort.closePort();
        } catch (Exception ex) {
            throw new BlinkyTapeControllerException("Couldn't close serial port: ", ex);
        }
    }

    @Override
    public void renderFrame(BlinkyFrame frame) {
        // Creates an array big enough to hold each LED color and the
        // terminator.
        byte[] data = new byte[(frame.getLightCount() + 1) * 3];
        int offset;
        for (int led = 0; led < frame.getLightCount(); led++) {
            // 3 bytes, RGB, limited to a maximum of 254 as 255 is special.
            offset = led * 3;
            data[offset] = (byte) Math.min(frame.getRedAtIndex(led), 254);
            data[offset + 1] = (byte) Math.min(frame.getGreenAtIndex(led), 254);
            data[offset + 2] = (byte) Math.min(frame.getBlueAtIndex(led), 254);
        }

        // The sketch only reads three bytes at a time so send 3 with the final
        // 0xFF
        offset = frame.getLightCount() * 3;
        data[offset] = 0x0;
        data[offset + 1] = 0x0;
        data[offset + 2] = (byte) 0xFF;

        try {
            this.serialPort.writeBytes(data);
        } catch (SerialPortException e) {
            throw new BlinkyTapeControllerException("Couldn't write to serial port: ", e);
        }
    }
}

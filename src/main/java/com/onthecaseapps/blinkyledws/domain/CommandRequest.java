package com.onthecaseapps.blinkyledws.domain;

public class CommandRequest {
    Command cmd;

    public Command getCMD() {
        return cmd;
    }

    public void setCMD(Command cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "CommandRequest{" +
                "cmd=" + cmd +
                '}';
    }
}
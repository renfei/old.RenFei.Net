package net.renfei.core.entity;

public enum LogINOUT {
    IN("IN"), OUT("OUT");
    private String inOut;

    LogINOUT(String inout) {
        this.inOut = inout;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }
}

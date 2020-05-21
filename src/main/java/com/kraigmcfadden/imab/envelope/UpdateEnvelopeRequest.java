package com.kraigmcfadden.imab.envelope;

public class UpdateEnvelopeRequest {

    private String name;
    private double allocated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAllocated() {
        return allocated;
    }

    public void setAllocated(double allocated) {
        this.allocated = allocated;
    }
}

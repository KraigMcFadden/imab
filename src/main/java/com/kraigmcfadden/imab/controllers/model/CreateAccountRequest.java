package com.kraigmcfadden.imab.controllers.model;

public class CreateAccountRequest {

    private String name;
    private double startingCash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStartingCash() {
        return startingCash;
    }

    public void setStartingCash(double startingCash) {
        this.startingCash = startingCash;
    }
}

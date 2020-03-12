package com.kraigmcfadden.imab.controllers.model;

public class CreateExpenseRequest {

    private String description;
    private double cost;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

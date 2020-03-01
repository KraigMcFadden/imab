package com.kraigmcfadden.imab.model;

public class Expense {

    private final Id id;
    private String description;
    private double cost;

    public Expense(String description, double cost) {
        this.id = new Id();
        this.description = description;
        this.cost = cost;
    }

    public Id getId() {
        return id;
    }

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

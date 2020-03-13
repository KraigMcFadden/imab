package com.kraigmcfadden.imab.controllers.model;

import com.kraigmcfadden.imab.domain.model.Timespan;

public class CreateBudgetRequest {

    private String name;
    private double limit;
    private Timespan timespan;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public Timespan getTimespan() {
        return timespan;
    }

    public void setTimespan(Timespan timespan) {
        this.timespan = timespan;
    }
}

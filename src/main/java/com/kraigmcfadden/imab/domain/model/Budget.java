package com.kraigmcfadden.imab.domain.model;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class Budget implements DomainModel, Aggregatable {

    private final Id id;
    private final Map<Id, Expense> expenses;
    private String name;
    private double limit;
    private Timespan timespan;

    public Budget(String name, double limit, Timespan timespan) {
        this.id = new Id();
        this.name = name;
        this.limit = limit;
        this.timespan = timespan;
        this.expenses = Maps.newHashMap();
    }

    public Id getId() {
        return id;
    }

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

    public double getCurrent() {
        double current = 0.0;
        for (Expense expense : getExpenses()) {
            current += expense.getCost();
        }
        return current;
    }

    public Timespan getTimespan() {
        return timespan;
    }

    public void setTimespan(Timespan timespan) {
        this.timespan = timespan;
    }

    public void addExpense(Expense expense) {
        expenses.put(expense.getId(), expense);
    }

    public Collection<Expense> getExpenses() {
        return expenses.values();
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense.getId());
    }

    public boolean isOverLimit() {
        return getCurrent() > getLimit();
    }
}

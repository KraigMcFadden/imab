package com.kraigmcfadden.imab.expense;

import java.util.Date;

public class Expense {

    private final String id;
    private final String description;
    private final double cost;
    private final Date date;
    private final String accountId;
    private final String envelopeId;
    private final String budgetId;

    private Expense(String id,
                    String description,
                    double cost,
                    Date date,
                    String accountId,
                    String envelopeId,
                    String budgetId) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.accountId = accountId;
        this.envelopeId = envelopeId;
        this.budgetId = budgetId;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public Date getDate() {
        return date;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEnvelopeId() {
        return envelopeId;
    }

    public String getBudgetId() {
        return budgetId;
    }
}

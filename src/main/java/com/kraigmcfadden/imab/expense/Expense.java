package com.kraigmcfadden.imab.expense;

import java.time.LocalDate;

public class Expense {

    private final String id;
    private final String description;
    private final double cost;
    private final LocalDate date;
    private final String accountId;
    private final String envelopeId;
    private final String budgetId;

    private Expense(String id,
                    String description,
                    double cost,
                    LocalDate date,
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

    public LocalDate getDate() {
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

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String description;
        private double cost;
        private LocalDate date;
        private String accountId;
        private String envelopeId;
        private String budgetId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCost(double cost) {
            this.cost = cost;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withEnvelopeId(String envelopeId) {
            this.envelopeId = envelopeId;
            return this;
        }

        public Builder withBudgetId(String budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public Expense build() {
            return new Expense(id, description, cost, date, accountId, envelopeId, budgetId);
        }
    }
}

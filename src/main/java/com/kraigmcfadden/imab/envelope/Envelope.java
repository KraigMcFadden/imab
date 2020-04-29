package com.kraigmcfadden.imab.envelope;

public class Envelope {

    private final String id;
    private final String name;
    private final double allocated;
    private final String accountId;
    private final String budgetId;

    private Envelope(String id,
                     String name,
                     double allocated,
                     String accountId,
                     String budgetId) {
        this.id = id;
        this.name = name;
        this.allocated = allocated;
        this.accountId = accountId;
        this.budgetId = budgetId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAllocated() {
        return allocated;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getBudgetId() {
        return budgetId;
    }
    
    public static Builder newBuilder() {
        return new Builder();
    }
    
    public static class Builder {
        private String id;
        private String name;
        private double allocatedMoney;
        private String accountId;
        private String budgetId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAllocatedMoney(double allocatedMoney) {
            this.allocatedMoney = allocatedMoney;
            return this;
        }

        public Builder withAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withBudgetId(String budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public Envelope build() {
            return new Envelope(id, name, allocatedMoney, accountId, budgetId);
        }
    }
}

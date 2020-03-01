package com.kraigmcfadden.imab.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Account {

    private final Id id;
    private final Map<Id, BudgetGroup> budgetGroups;
    private String name;
    private double cash;
    private double limit;
    private double current;

    public Account(String name, double cash) {
        this.id = new Id();
        this.name = name;
        this.cash = cash;
        this.budgetGroups = Maps.newHashMap();
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

    public double getCurrent() {
        return current;
    }

    public double getCash() {
        return cash;
    }

    public void addCash(double amount) {
        cash += amount;
    }

    public Collection<BudgetGroup> getBudgetGroups() {
        return budgetGroups.values();
    }

    public void addBudgetGroup(BudgetGroup budgetGroup) {
        budgetGroups.put(budgetGroup.getId(), budgetGroup);
        limit += budgetGroup.getLimit();
        current += budgetGroup.getCurrent();
    }

    public void removeBudgetGroup(BudgetGroup budgetGroup) {
        budgetGroups.remove(budgetGroup.getId());
        limit -= budgetGroup.getLimit();
        current -= budgetGroup.getCurrent();
    }

    public boolean isOverLimit() {
        return current > limit;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private double startingCash;
        private List<BudgetGroup> groups = Lists.newArrayList();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withStartingCash(double startingCash) {
            this.startingCash = startingCash;
            return this;
        }

        public Builder withBudgetGroup(BudgetGroup group) {
            this.groups.add(group);
            return this;
        }

        public Account build() {
            Account account = new Account(name, startingCash);
            for (BudgetGroup group : groups) {
                account.addBudgetGroup(group);
            }
            return account;
        }
    }
}

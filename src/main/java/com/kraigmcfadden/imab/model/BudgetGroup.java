package com.kraigmcfadden.imab.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BudgetGroup {

    private final Id id;
    private final Map<Id, Budget> budgets;
    private String name;

    public BudgetGroup(String name) {
        this.id = new Id();
        this.name = name;
        this.budgets = Maps.newHashMap();
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
        double limit = 0.0;
        for (Budget budget : budgets.values()) {
            limit += budget.getLimit();
        }
        return limit;
    }

    public double getCurrent() {
        double current = 0.0;
        for (Budget budget : budgets.values()) {
            current += budget.getCurrent();
        }
        return current;
    }

    public Collection<Budget> getBudgets() {
        return budgets.values();
    }

    public void addBudget(Budget budget) {
        budgets.put(budget.getId(), budget);
    }

    public void removeBudget(Budget budget) {
        budgets.remove(budget.getId());
    }

    public boolean isOverLimit() {
        return getCurrent() > getLimit();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private List<Budget> budgets = Lists.newArrayList();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBudget(Budget budget) {
            this.budgets.add(budget);
            return this;
        }

        public BudgetGroup build() {
            BudgetGroup budgetGroup = new BudgetGroup(name);
            for (Budget budget : budgets) {
                budgetGroup.addBudget(budget);
            }
            return budgetGroup;
        }
    }
}

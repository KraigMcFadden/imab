package com.kraigmcfadden.imab.persistence.model;

import java.util.List;

public class BudgetPO implements PO {

    private String id;
    private String name;
    private String accountId;
    private String parentId;
    private double limit;
    private List<String> expenseIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public List<String> getExpenseIds() {
        return expenseIds;
    }

    public void setExpenseIds(List<String> expenseIds) {
        this.expenseIds = expenseIds;
    }
}

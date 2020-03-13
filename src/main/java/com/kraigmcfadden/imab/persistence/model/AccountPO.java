package com.kraigmcfadden.imab.persistence.model;

import java.util.List;

public class AccountPO implements PO {

    private String id;
    private String name;
    private double cash;
    private List<String> childIds;

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

    public double getCash() {
        return cash;
    }
    public void setCash(double cash) {
        this.cash = cash;
    }

    public List<String> getChildIds() {
        return childIds;
    }
    public void setChildIds(List<String> childIds) {
        this.childIds = childIds;
    }
}

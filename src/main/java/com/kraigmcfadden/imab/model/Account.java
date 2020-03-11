package com.kraigmcfadden.imab.model;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Account {

    private final Id id;
    private final Map<Id, Group> groups;
    private String name;
    private double cash;
    private double limit;
    private double current;

    public Account(String name, double cash) {
        this.id = new Id();
        this.name = name;
        this.cash = cash;
        this.groups = Maps.newHashMap();
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

    public Collection<Group> getGroups() {
        return groups.values();
    }

    public Optional<Group> getGroup(Id id) {
        return Optional.ofNullable(groups.get(id));
    }

    public void addGroup(Group group) {
        groups.put(group.getId(), group);
        limit += group.getLimit();
        current += group.getCurrent();
    }

    public void removeGroup(Group group) {
        groups.remove(group.getId());
        limit -= group.getLimit();
        current -= group.getCurrent();
    }

    public boolean isOverLimit() {
        return current > limit;
    }
}

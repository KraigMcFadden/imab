package com.kraigmcfadden.imab.model;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Group  implements Aggregatable {

    private final Id id;
    private final Map<Id, Aggregatable> children;
    private String name;

    public Group(String name) {
        this.id = new Id();
        this.name = name;
        this.children = Maps.newHashMap();
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
        for (Aggregatable aggregatable : children.values()) {
            limit += aggregatable.getLimit();
        }
        return limit;
    }

    public double getCurrent() {
        double current = 0.0;
        for (Aggregatable aggregatable : children.values()) {
            current += aggregatable.getCurrent();
        }
        return current;
    }

    public Collection<Aggregatable> getChildren() {
        return children.values();
    }

    public Optional<Aggregatable> getChild(Id id) {
        return Optional.ofNullable(children.get(id));
    }

    public void addChild(Aggregatable aggregatable) {
        children.put(aggregatable.getId(), aggregatable);
    }

    public void removeChild(Aggregatable aggregatable) {
        children.remove(aggregatable.getId());
    }

    public boolean isOverLimit() {
        return getCurrent() > getLimit();
    }
}

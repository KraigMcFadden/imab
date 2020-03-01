package com.kraigmcfadden.imab.model;

import java.util.Objects;
import java.util.UUID;

public class Id implements Comparable {

    private final String id;

    public Id() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Object o) {
        Id that = (Id) o;
        return this.id.compareTo(that.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Id{" +
                "id='" + id + '\'' +
                '}';
    }
}

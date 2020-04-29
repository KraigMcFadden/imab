package com.kraigmcfadden.imab.budget;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class InMemoryBudgetRepository implements BudgetRepository {

    private final Map<String, Budget> database = Maps.newHashMap();

    @Override
    public Optional<Budget> get(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void create(Budget budget) {
        database.put(budget.getId(), budget);
    }

    @Override
    public void update(Budget budget) {
        database.put(budget.getId(), budget);
    }

    @Override
    public void delete(String id) {
        database.remove(id);
    }
}

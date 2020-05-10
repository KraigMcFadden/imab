package com.kraigmcfadden.imab.expense;

import com.google.common.collect.Maps;
import com.kraigmcfadden.imab.envelope.Envelope;

import java.util.Map;
import java.util.Optional;

public class InMemoryExpenseRepository implements ExpenseRepository {

    private final Map<String, Expense> database = Maps.newHashMap();

    @Override
    public Optional<Expense> get(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void create(Expense expense) {
        database.put(expense.getId(), expense);
    }

    @Override
    public void update(Expense expense) {
        database.put(expense.getId(), expense);
    }

    @Override
    public void delete(String id) {
        database.remove(id);
    }
}

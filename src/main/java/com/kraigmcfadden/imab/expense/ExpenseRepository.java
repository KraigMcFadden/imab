package com.kraigmcfadden.imab.expense;

import java.util.Optional;

public interface ExpenseRepository {

    Optional<Expense> get(String id);
    void create(Expense expense);
    void update(Expense expense);
    void delete(String id);
}

package com.kraigmcfadden.imab.budget;

import java.util.Optional;

public interface BudgetRepository {

    Optional<Budget> get(String id);
    void create(Budget budget);
    void update(Budget budget);
    void delete(String id);
}

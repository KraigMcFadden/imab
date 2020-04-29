package com.kraigmcfadden.imab.budget;

import com.kraigmcfadden.imab.common.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class BudgetWorker {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetWorker(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget create(double openingBalance) {
        Budget budget = Budget.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withOpeningBalance(openingBalance)
                .build();
        budgetRepository.create(budget);
        return budget;
    }

    public Budget get(String id) throws NotFoundException {
        Optional<Budget> budget = budgetRepository.get(id);
        return budget.orElseThrow(() -> { throw new NotFoundException("Budget with id " + id + " not found in database"); });
    }
}

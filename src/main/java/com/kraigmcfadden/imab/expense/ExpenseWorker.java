package com.kraigmcfadden.imab.expense;

import com.kraigmcfadden.imab.common.NotFoundException;
import com.kraigmcfadden.imab.envelope.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class ExpenseWorker {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseWorker(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense create(String description, double cost, LocalDate date, String envelopeId, String budgetId) {
        Expense expense = Expense.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withDescription(description)
                .withCost(cost)
                .withDate(date)
                .withEnvelopeId(envelopeId)
                .withBudgetId(budgetId)
                .withAccountId("123")
                .build();
        expenseRepository.create(expense);
        return expense;
    }

    public Expense get(String id) throws NotFoundException {
        Optional<Expense> expense = expenseRepository.get(id);
        return expense.orElseThrow(() -> { throw new NotFoundException("Expense with id " + id + " not found in database"); });
    }

    public Expense update(ExpenseUpdate expenseUpdate) {
        Expense.Builder builder = Expense.newBuilder()
                .from(get(expenseUpdate.getExpenseId()));
        expenseUpdate.getNewExpenseCost().ifPresent(builder::withCost);
        expenseUpdate.getNewExpenseDate().ifPresent(builder::withDate);
        expenseUpdate.getNewExpenseDescription().ifPresent(builder::withDescription);
        expenseUpdate.getNewExpenseEnvelopeId().ifPresent(builder::withEnvelopeId);
        Expense expense = builder.build();
        expenseRepository.update(expense);
        return expense;
    }
}

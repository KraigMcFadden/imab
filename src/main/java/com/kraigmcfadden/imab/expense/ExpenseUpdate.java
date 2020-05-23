package com.kraigmcfadden.imab.expense;

import java.time.LocalDate;
import java.util.Optional;

public class ExpenseUpdate {

    private final String expenseId;
    private String newExpenseDescription;
    private Double newExpenseCost;
    private LocalDate newExpenseDate;
    private String newExpenseEnvelopeId;

    public ExpenseUpdate(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public Optional<String> getNewExpenseDescription() {
        return Optional.ofNullable(newExpenseDescription);
    }

    public ExpenseUpdate setNewExpenseDescription(String newExpenseDescription) {
        this.newExpenseDescription = newExpenseDescription;
        return this;
    }

    public Optional<Double> getNewExpenseCost() {
        return Optional.ofNullable(newExpenseCost);
    }

    public ExpenseUpdate setNewExpenseCost(double newExpenseCost) {
        this.newExpenseCost = newExpenseCost;
        return this;
    }

    public Optional<LocalDate> getNewExpenseDate() {
        return Optional.ofNullable(newExpenseDate);
    }

    public ExpenseUpdate setNewExpenseDate(LocalDate newExpenseDate) {
        this.newExpenseDate = newExpenseDate;
        return this;
    }

    public Optional<String> getNewExpenseEnvelopeId() {
        return Optional.ofNullable(newExpenseEnvelopeId);
    }

    public ExpenseUpdate setNewExpenseEnvelopeId(String newExpenseEnvelopeId) {
        this.newExpenseEnvelopeId = newExpenseEnvelopeId;
        return this;
    }
}

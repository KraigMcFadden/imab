package com.kraigmcfadden.imab.services;

import com.kraigmcfadden.imab.model.Account;
import com.kraigmcfadden.imab.model.Budget;
import com.kraigmcfadden.imab.model.BudgetGroup;
import com.kraigmcfadden.imab.model.Timespan;
import org.springframework.stereotype.Component;

@Component
public class AccountManagementService {

    public Account createAccount(String name, double startingCash) {
        return new Account(name, startingCash);
    }

    public Account createDefaultAccount(String name, double startingCash) {
//        Account account = createAccount(name, startingCash);
//
//        // TODO: Consider making categories for things like fixed expenses, then stuff like "housing" or "car" would
//        // TODO: be groups under that, and then line items would be stuff like "rent", "water", etc.
//        BudgetGroup fixedExpenses = new BudgetGroup("Fixed Expenses");
//        Budget housing = new Budget("Housing", 1000, Timespan.MONTHLY);
//        Budget utilities = new Budget("Utilities", 200, Timespan.MONTHLY);
//        Budget carPayment = new Budget("Car Payment", 1000, Timespan.MONTHLY);
//        Budget carInsurance = new Budget("Car Insurance", 600, Timespan.SIX_MONTHS);
//
//        BudgetGroup variableExpenses = new BudgetGroup("Variable Expenses");
//        Budget groceries = new Budget("Groceries", 100, Timespan.WEEKLY);

        return Account.builder()
                .withName(name)
                .withStartingCash(startingCash)
                .withBudgetGroup(BudgetGroup.builder()
                        .withName("Fixed Expenses")
                        .withBudget(Budget.builder()
                                .withName("Housing")
                                .withLimit(1000)
                                .withTimespan(Timespan.MONTHLY)
                                .build())
                        .withBudget(Budget.builder()
                                .withName("Utilities")
                                .withLimit(200)
                                .withTimespan(Timespan.MONTHLY)
                                .build())
                        .withBudget(Budget.builder()
                                .withName("Car Payment")
                                .withLimit(1000)
                                .withTimespan(Timespan.MONTHLY)
                                .build())
                        .withBudget(Budget.builder()
                                .withName("Car Insurance")
                                .withLimit(700)
                                .withTimespan(Timespan.SIX_MONTHS)
                                .build())
                        .build())
                .withBudgetGroup(BudgetGroup.builder()
                        .withName("Variable Expenses")
                        .withBudget(Budget.builder()
                                .withName("Groceries")
                                .withLimit(100)
                                .withTimespan(Timespan.WEEKLY)
                                .build())
                        .build())
                .build();
    }
}

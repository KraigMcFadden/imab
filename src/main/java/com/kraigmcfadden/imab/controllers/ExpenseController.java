package com.kraigmcfadden.imab.controllers;

import com.google.common.collect.Lists;
import com.kraigmcfadden.imab.model.*;
import com.kraigmcfadden.imab.services.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {

    private static final String ACCOUNTS_RESOURCE = "/accounts";
    private static final String BUDGET_GROUPS_RESOURCE = ACCOUNTS_RESOURCE + "/{accountId}/budget-groups";
    private static final String BUDGETS_RESOURCE = BUDGET_GROUPS_RESOURCE + "/{budgetGroupId}/budgets";
    private static final String EXPENSES_RESOURCE = BUDGETS_RESOURCE + "/{budgetId}/expenses";

    private final PersistenceService persistenceService;

    @Autowired
    public ExpenseController(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @RequestMapping(value = BUDGET_GROUPS_RESOURCE, method = RequestMethod.GET)
    public ResponseEntity<List<BudgetGroup>> getAllBudgetGroups(@RequestParam("{accountId}") String accountId) {
        Id id = Id.of(accountId);
        Optional<Account> accountOptional = persistenceService.getAccountById(id);
        if (accountOptional.isPresent()) {
            List<BudgetGroup> budgetGroups = Lists.newArrayList();
            budgetGroups.addAll(accountOptional.get().getBudgetGroups());
            return ResponseEntity.ok(budgetGroups);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = BUDGET_GROUPS_RESOURCE, method = RequestMethod.POST)
    public ResponseEntity<Id> createBudgetGroup(@PathVariable String accountId,
                                                @RequestBody CreateBudgetGroupRequest request) {
        Id id = Id.of(accountId);
        BudgetGroup budgetGroup = new BudgetGroup(request.getName());
        persistenceService.saveBudgetGroup(budgetGroup);
        return ResponseEntity.ok(budgetGroup.getId());
    }

    @RequestMapping(value = BUDGETS_RESOURCE, method = RequestMethod.GET)
    public ResponseEntity<List<Budget>> getAllBudgets(@RequestParam("{accountId}") String accountId,
                                                      @RequestParam("{budgetGroupId}") String budgetGroupId) {
        Id id = Id.of(budgetGroupId);
        Optional<BudgetGroup> budgetGroupOptional = persistenceService.getBudgetGroupById(id);
        if (budgetGroupOptional.isPresent()) {
            List<Budget> budgets = Lists.newArrayList();
            budgets.addAll(budgetGroupOptional.get().getBudgets());
            return ResponseEntity.ok(budgets);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = EXPENSES_RESOURCE, method = RequestMethod.GET)
    public ResponseEntity<List<Expense>> getAllExpenses(@RequestParam("{accountId}") String accountId,
                                                        @RequestParam("{budgetGroupId}") String budgetGroupId,
                                                        @RequestParam("{budgetId}") String budgetId) {
        Id id = Id.of(budgetId);
        Optional<Budget> budgetOptional = persistenceService.getBudgetById(id);
        if (budgetOptional.isPresent()) {
            List<Expense> expenses = Lists.newArrayList();
            expenses.addAll(budgetOptional.get().getExpenses());
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

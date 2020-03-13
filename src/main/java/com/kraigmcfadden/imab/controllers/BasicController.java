package com.kraigmcfadden.imab.controllers;

import com.google.common.collect.Lists;
import com.kraigmcfadden.imab.controllers.model.*;
import com.kraigmcfadden.imab.domain.model.*;
import com.kraigmcfadden.imab.domain.repository.AccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BasicController {

    private static final Log log = LogFactory.getLog(BasicController.class);

    private final AccountRepository accountRepository;

    @Autowired
    public BasicController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        log.info("Account creation requested");

        Account account = Account.create(createAccountRequest.getName(), createAccountRequest.getStartingCash());
        account.addGroup(new Group("My Budgets"));
        accountRepository.save(account);

        CreateAccountResponse response = new CreateAccountResponse();
        response.setId(account.getId().getId());
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/accounts/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        log.info("Account " + accountId + " requested");
        Optional<Account> accountOptional = getAccount(Id.of(accountId));
        return accountOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    private Optional<Account> getAccount(Id accountId) {
        return accountRepository.lookup(accountId);
    }

    // TODO: make group its own resource with its own persistence service
    @RequestMapping(path = "/accounts/{accountId}/groups", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createGroup(@PathVariable String accountId,
                                              @RequestBody CreateGroupRequest createGroupRequest) {
        log.info("Group creation requested");
        Optional<Account> accountOptional = getAccount(Id.of(accountId));
        if (accountOptional.isPresent()) {
            Group group = new Group(createGroupRequest.getName());
            accountOptional.get().addGroup(group);
            return ResponseEntity.ok(group.getId().getId());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/accounts/{accountId}/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Group>> getAllGroups(@PathVariable String accountId) {
        log.info("All groups request for account " + accountId);
        Optional<Account> accountOptional = getAccount(Id.of(accountId));
        return accountOptional
                .<ResponseEntity<List<Group>>>map(account -> ResponseEntity.ok(Lists.newArrayList(account.getGroups())))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Group> getGroup(@PathVariable String accountId,
                                          @PathVariable String groupId) {
        log.info("Group " + groupId + " requested");
        Optional<Group> groupOptional = getGroup(Id.of(accountId), Id.of(groupId));
        return groupOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    private Optional<Group> getGroup(Id accountId, Id groupId) {
        Optional<Account> accountOptional = getAccount(accountId);
        Optional<Group> groupOptional = Optional.empty();
        if (accountOptional.isPresent()) {
            groupOptional = accountOptional.get().getGroup(groupId);
        }
        return groupOptional;
    }

    // TODO: make budget its own resource with its own persistence service
    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createBudget(@PathVariable String accountId,
                                               @PathVariable String groupId,
                                               @RequestBody CreateBudgetRequest createBudgetRequest) {
        log.info("Budget creation requested");
        Optional<Group> groupOptional = getGroup(Id.of(accountId), Id.of(groupId));
        if (groupOptional.isPresent()) {
            Budget budget = new Budget(createBudgetRequest.getName(), createBudgetRequest.getLimit(), createBudgetRequest.getTimespan());
            groupOptional.get().addChild(budget);
            return ResponseEntity.created(URI.create("/accounts/" + accountId + "/groups/" + groupId + "/budgets/" + budget.getId().getId())).build();
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Budget>> getAllBudgets(@PathVariable String accountId,
                                                      @PathVariable String groupId) {
        log.info("All budgets requested for group " + groupId);
        Optional<Group> groupOptional = getGroup(Id.of(accountId), Id.of(groupId));
        if (groupOptional.isPresent()) {
            List<Budget> budgets = groupOptional.get().getChildren()
                    .stream()
                    .filter(a -> a instanceof Budget)
                    .map(b -> (Budget) b)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(budgets);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets/{budgetId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Budget> getBudget(@PathVariable String accountId,
                                            @PathVariable String groupId,
                                            @PathVariable String budgetId) {
        log.info("Budget " + budgetId + " requested");
        Optional<Budget> budgetOptional = getBudget(Id.of(accountId), Id.of(groupId), Id.of(budgetId));
        return budgetOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    private Optional<Budget> getBudget(Id accountId, Id groupId, Id budgetId) {
        Optional<Group> groupOptional = getGroup(accountId, groupId);
        Optional<Budget> budgetOptional = Optional.empty();
        if (groupOptional.isPresent()) {
            Optional<Aggregatable> childOption = groupOptional.get().getChild(budgetId);
            if (childOption.isPresent() && childOption.get() instanceof Budget) {
                budgetOptional = Optional.of((Budget) childOption.get());
            }
        }
        return budgetOptional;
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets/{budgetId}/expenses", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createExpense(@PathVariable String accountId,
                                                @PathVariable String groupId,
                                                @PathVariable String budgetId,
                                                @RequestBody CreateExpenseRequest createExpenseRequest) {
        log.info("Expense creation requested");
        Optional<Budget> budgetOptional = getBudget(Id.of(accountId), Id.of(groupId), Id.of(budgetId));
        if (budgetOptional.isPresent()) {
            Expense expense = new Expense(createExpenseRequest.getDescription(), createExpenseRequest.getCost());
            budgetOptional.get().addExpense(expense);
            return ResponseEntity.ok(expense.getId().getId());
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets/{budgetId}/expenses/{expenseId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Budget> getExpense(@PathVariable String accountId,
                                             @PathVariable String groupId,
                                             @PathVariable String budgetId,
                                             @PathVariable String expenseId) {
        log.info("Expense " + expenseId + " requested");
        Optional<Budget> budgetOptional = getBudget(Id.of(accountId), Id.of(groupId), Id.of(budgetId));
        //if (budgetOptional.isPresent()) {
            //Optional<Expense> expenseOptional = budgetOptional.get().g
        //}
        return ResponseEntity.badRequest().body(null);
    }
}

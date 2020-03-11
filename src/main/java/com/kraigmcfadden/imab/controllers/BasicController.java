package com.kraigmcfadden.imab.controllers;

import com.google.common.base.Throwables;
import com.kraigmcfadden.imab.controllers.model.CreateAccountRequest;
import com.kraigmcfadden.imab.controllers.model.CreateBudgetRequest;
import com.kraigmcfadden.imab.controllers.model.CreateGroupRequest;
import com.kraigmcfadden.imab.model.*;
import com.kraigmcfadden.imab.services.AccountManagementService;
import com.kraigmcfadden.imab.services.PersistenceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
public class BasicController {

    private static final Log log = LogFactory.getLog(BasicController.class);

    private static final String INJECTION_TOKEN = "${inject}";
    private static final String HTML_TEMPLATE = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <title>IMAB</title>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "    <link rel = \"stylesheet\" type = \"text/css\" href = \"style.css\" />\n" +
            "</head>\n" +
            "<body>\n" +
            INJECTION_TOKEN + "\n" +
            "</body>\n" +
            "</html>";

    private final PersistenceService persistenceService;

    @Autowired
    public BasicController(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        log.info("Account creation requested");
        Account account = new Account(createAccountRequest.getName(), createAccountRequest.getStartingCash());
        account.addGroup(new Group("My Budgets"));
        persistenceService.saveAccount(account);
        return ResponseEntity.ok(account.getId().getId());
    }

    @RequestMapping(path = "/accounts/{accountId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        log.info("Account " + accountId + " requested");
        Optional<Account> accountOptional = persistenceService.getAccountById(Id.of(accountId));
        return accountOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    // TODO: make group its own resource with its own persistence service
    @RequestMapping(path = "/accounts/{accountId}/groups", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createGroup(@PathVariable String accountId,
                                              @RequestBody CreateGroupRequest createGroupRequest) {
        log.info("Group creation requested");
        Optional<Account> accountOptional = persistenceService.getAccountById(Id.of(accountId));
        if (accountOptional.isPresent()) {
            Group group = new Group(createGroupRequest.getName());
            accountOptional.get().addGroup(group);
            return ResponseEntity.ok(group.getId().getId());
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Group> getGroup(@PathVariable String accountId,
                                          @PathVariable String groupId) {
        log.info("Group " + groupId + " requested");
        Optional<Account> accountOptional = persistenceService.getAccountById(Id.of(accountId));
        if (accountOptional.isPresent()) {
            Optional<Group> groupOptional = accountOptional.get().getGroup(Id.of(groupId));
            if (groupOptional.isPresent()) {
                return ResponseEntity.ok(groupOptional.get());
            }
        }
        return ResponseEntity.badRequest().body(null);
    }

    // TODO: make budget its own resource with its own persistence service
    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createBudget(@PathVariable String accountId,
                                               @PathVariable String groupId,
                                               @RequestBody CreateBudgetRequest createBudgetRequest) {
        log.info("Budget creation requested");
        Optional<Account> accountOptional = persistenceService.getAccountById(Id.of(accountId));
        if (accountOptional.isPresent()) {
            Optional<Group> groupOptional = accountOptional.get().getGroup(Id.of(groupId));
            if (groupOptional.isPresent()) {
                Budget budget = new Budget(createBudgetRequest.getName(), createBudgetRequest.getLimit(), createBudgetRequest.getTimespan());
                groupOptional.get().addChild(budget);
                return ResponseEntity.created(URI.create("/accounts/" + accountId + "/groups/" + groupId + "/budgets/" + budget.getId().getId())).build();
            }
        }
        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(path = "/accounts/{accountId}/groups/{groupId}/budgets/{budgetId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Budget> getBudget(@PathVariable String accountId,
                                            @PathVariable String groupId,
                                            @PathVariable String budgetId) {
        log.info("Budget " + budgetId + " requested");
        Optional<Account> accountOptional = persistenceService.getAccountById(Id.of(accountId));
        if (accountOptional.isPresent()) {
            Optional<Group> groupOptional = accountOptional.get().getGroup(Id.of(groupId));
            if (groupOptional.isPresent()) {
                Optional<Aggregatable> childOptional = groupOptional.get().getChild(Id.of(budgetId));
                if (childOptional.isPresent() && childOptional.get() instanceof Budget) {
                    return ResponseEntity.ok((Budget) childOptional.get());
                }
            }
        }
        return ResponseEntity.badRequest().body(null);
    }
}

package com.kraigmcfadden.imab.budget;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraigmcfadden.imab.common.NotFoundException;
import com.kraigmcfadden.imab.common.ValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class BudgetService {

    private static final Log log = LogFactory.getLog(BudgetService.class);

    private static final String RESOURCE = "/budgets";

    private final BudgetValidator budgetValidator;
    private final BudgetWorker budgetWorker;

    @Autowired
    public BudgetService(BudgetValidator budgetValidator, BudgetWorker budgetWorker) {
        this.budgetValidator = budgetValidator;
        this.budgetWorker = budgetWorker;
    }

    @RequestMapping(path = RESOURCE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBudget(@RequestBody CreateBudgetRequest request) {
        try {
            log.info("Create budget requested with request " + new ObjectMapper().writeValueAsString(request));
            budgetValidator.validateCreateBudget(request);
            Budget budget = budgetWorker.create(request.getOpeningBalance());
            return ResponseEntity.created(URI.create(RESOURCE + "/" + budget.getId())).build();
        } catch (ValidationException e) {
            log.error("Invalid budget creation request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Could not create budget", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = RESOURCE + "/{budgetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> getBudget(@PathVariable String budgetId) {
        log.info("Get budget " + budgetId + " requested");
        try {
            Budget budget = budgetWorker.get(budgetId);
            return ResponseEntity.ok(budget);
        } catch (ValidationException e) {
            log.error("Invalid input budget id " + budgetId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("Budget id not found in our database " + budgetId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not get budget " + budgetId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

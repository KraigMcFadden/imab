package com.kraigmcfadden.imab.expense;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraigmcfadden.imab.common.NotFoundException;
import com.kraigmcfadden.imab.common.ValidationException;
import com.kraigmcfadden.imab.envelope.Envelope;
import com.kraigmcfadden.imab.envelope.EnvelopeUpdate;
import com.kraigmcfadden.imab.envelope.UpdateEnvelopeRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ExpenseService {

    private static final Log log = LogFactory.getLog(ExpenseService.class);

    private static final String RESOURCE = "/expenses";

    private final ExpenseWorker expenseWorker;

    @Autowired
    public ExpenseService(ExpenseWorker expenseWorker) {
        this.expenseWorker = expenseWorker;
    }

    @RequestMapping(path = RESOURCE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEnvelope(@RequestBody CreateExpenseRequest request) {
        try {
            log.info("Create expense requested with request " + new ObjectMapper().writeValueAsString(request));
            //budgetValidator.validateCreateBudget(request);
            Expense expense = expenseWorker.create(
                    request.getDescription(),
                    request.getCost(),
                    request.getDate(),
                    request.getEnvelopeId(),
                    request.getBudgetId()
            );
            return ResponseEntity.created(URI.create(RESOURCE + "/" + expense.getId())).build();
        } catch (ValidationException e) {
            log.error("Invalid expense creation request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Could not create expense", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = RESOURCE + "/{expenseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Expense> getEnvelope(@PathVariable String expenseId) {
        log.info("Get expense " + expenseId + " requested");
        try {
            Expense expense = expenseWorker.get(expenseId);
            return ResponseEntity.ok(expense);
        } catch (ValidationException e) {
            log.error("Invalid input expense id " + expenseId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("Expense id not found in our database " + expenseId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not get expense " + expenseId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = RESOURCE + "/{expenseId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Expense> updateExpense(@PathVariable String expenseId,
                                                 @RequestBody UpdateExpenseRequest request) {
        try {
            log.info("Update expense " + expenseId + " requested with request " + new ObjectMapper().writeValueAsString(request));
            // TODO: validation layer
            Expense expense = expenseWorker.update(
                    new ExpenseUpdate(expenseId)
                            .setNewExpenseDescription(request.getDescription())
                            .setNewExpenseCost(request.getCost())
                            .setNewExpenseDate(request.getDate())
                            .setNewExpenseEnvelopeId(request.getEnvelopeId())
            );
            return ResponseEntity.ok(expense);
        } catch (ValidationException e) {
            log.error("Invalid input expense id " + expenseId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("Expense id not found in our database " + expenseId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not update expense " + expenseId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

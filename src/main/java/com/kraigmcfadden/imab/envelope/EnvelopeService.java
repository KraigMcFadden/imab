package com.kraigmcfadden.imab.envelope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kraigmcfadden.imab.budget.Budget;
import com.kraigmcfadden.imab.budget.BudgetValidator;
import com.kraigmcfadden.imab.budget.BudgetWorker;
import com.kraigmcfadden.imab.budget.CreateBudgetRequest;
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
public class EnvelopeService {

    private static final Log log = LogFactory.getLog(EnvelopeService.class);

    private static final String RESOURCE = "/envelopes";

    private final EnvelopeWorker envelopeWorker;

    @Autowired
    public EnvelopeService(EnvelopeWorker envelopeWorker) {
        this.envelopeWorker = envelopeWorker;
    }

    @RequestMapping(path = RESOURCE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEnvelope(@RequestBody CreateEnvelopeRequest request) {
        try {
            log.info("Create envelope requested with request " + new ObjectMapper().writeValueAsString(request));
            //budgetValidator.validateCreateBudget(request);
            Envelope envelope = envelopeWorker.create(request.getName(), request.getAllocated(), request.getBudgetId());
            return ResponseEntity.created(URI.create(RESOURCE + "/" + envelope.getId())).build();
        } catch (ValidationException e) {
            log.error("Invalid envelope creation request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Could not create envelope", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = RESOURCE + "/{envelopeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Envelope> getEnvelope(@PathVariable String envelopeId) {
        log.info("Get envelope " + envelopeId + " requested");
        try {
            Envelope envelope = envelopeWorker.get(envelopeId);
            return ResponseEntity.ok(envelope);
        } catch (ValidationException e) {
            log.error("Invalid input envelope id " + envelopeId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("Envelope id not found in our database " + envelopeId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not get envelope " + envelopeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

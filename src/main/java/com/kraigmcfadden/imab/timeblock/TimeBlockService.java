package com.kraigmcfadden.imab.timeblock;

import com.kraigmcfadden.imab.common.NotFoundException;
import com.kraigmcfadden.imab.common.ValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class TimeBlockService {

    private static final Log log = LogFactory.getLog(TimeBlockService.class);

    private final TimeBlockValidator timeBlockValidator;
    private final TimeBlockWorker timeBlockWorker;

    @Autowired
    public TimeBlockService(TimeBlockValidator timeBlockValidator, TimeBlockWorker timeBlockWorker) {
        this.timeBlockValidator = timeBlockValidator;
        this.timeBlockWorker = timeBlockWorker;
    }

    @RequestMapping(path = "/accounts/{accountId}/timeblocks", method = RequestMethod.POST)
    public ResponseEntity<Void> createTimeBlock(@PathVariable String accountId,
                                                @RequestBody CreateTimeBlockRequest request) {
        log.info(String.format("Create time block requested with account id %s, start date %s, end date %s, and opening balance %s",
                accountId, request.getStartDate(), request.getEndDate(), request.getOpeningBalance()));
        try {
            timeBlockValidator.validateCreateTimeBlock(accountId, request);
            TimeBlock timeBlock = timeBlockWorker.createTimeBlock(
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getOpeningBalance(),
                    accountId);
            return ResponseEntity.created(URI.create("/timeblocks/" + timeBlock.getId())).build();
        } catch (ValidationException e) {
            log.error("Invalid time block create request", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Could not create time block", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = "/timeblocks/{timeBlockId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeBlock> getTimeBlock(@PathVariable String timeBlockId) {
        log.info("Get time block " + timeBlockId + " requested");
        try {
            timeBlockValidator.validateGetTimeBlock(timeBlockId);
            TimeBlock timeBlock = timeBlockWorker.getTimeBlock(timeBlockId);
            return ResponseEntity.ok(timeBlock);
        } catch (ValidationException e) {
            log.error("Invalid input time block id " + timeBlockId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("TimeBlock id not found in our database " + timeBlockId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not get time block " + timeBlockId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = "/accounts/{accountId}/timeblocks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, TimeBlock>> getAllTimeBlocksForAccount(@PathVariable String accountId) {
        log.info("Get all time blocks for account " + accountId + " requested");
        try {
            timeBlockValidator.validateGetAllTimeBlocksForAccount(accountId);
            List<TimeBlock> timeBlocks = timeBlockWorker.getAllTimeBlocksForAccount(accountId);
            return ResponseEntity.ok(timeBlocks.stream().collect(Collectors.toMap(TimeBlock::getId, Function.identity())));
        } catch (ValidationException e) {
            log.error("Invalid input account id " + accountId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("TimeBlocks for account " + accountId + " not found in our database", e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not get time blocks for account " + accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

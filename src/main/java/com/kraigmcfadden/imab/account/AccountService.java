package com.kraigmcfadden.imab.account;

import com.kraigmcfadden.imab.common.NotFoundException;
import com.kraigmcfadden.imab.common.ValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AccountService {

    private static final Log log = LogFactory.getLog(AccountService.class);

    private final AccountValidator accountValidator;
    private final AccountWorker accountWorker;

    @Autowired
    public AccountService(AccountValidator accountValidator, AccountWorker accountWorker) {
        this.accountValidator = accountValidator;
        this.accountWorker = accountWorker;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.POST)
    public ResponseEntity<Void> createAccount() {
        log.info("Create account requested");
        try {
            Account account = accountWorker.createAccount();
            return ResponseEntity.created(URI.create("/accounts/" + account.getId())).build();
        } catch (Exception e) {
            log.error("Could not create account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(path = "/accounts/{accountId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        log.info("Get account " + accountId + " requested");
        try {
            Account account = accountWorker.getAccount(accountId);
            return ResponseEntity.ok(account);
        } catch (ValidationException e) {
            log.error("Invalid input account id " + accountId, e);
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            log.error("Account id not found in our database " + accountId, e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Could not get account " + accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.kraigmcfadden.imab.account;

import com.kraigmcfadden.imab.common.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountWorker {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountWorker(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount() {
        Account account = Account.newBuilder()
                .withId(UUID.randomUUID().toString())
                .build();
        accountRepository.createAccount(account);
        return account;
    }

    public Account getAccount(String id) throws NotFoundException {
        Optional<Account> account = accountRepository.getAccount(id);
        return account.orElseThrow(() -> { throw new NotFoundException("Account with id " + id + " not found in database"); });
    }
}

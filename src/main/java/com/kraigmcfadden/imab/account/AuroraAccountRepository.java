package com.kraigmcfadden.imab.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Optional;

public class AuroraAccountRepository implements AccountRepository {

    private static final Log log = LogFactory.getLog(AuroraAccountRepository.class);

    @Override
    public Optional<Account> getAccount(String id) {
        return Optional.empty();
    }

    @Override
    public void createAccount(Account account) {

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(String id) {

    }
}

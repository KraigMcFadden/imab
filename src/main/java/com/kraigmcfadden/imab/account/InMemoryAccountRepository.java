package com.kraigmcfadden.imab.account;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> database = Maps.newHashMap();

    @Override
    public Optional<Account> getAccount(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void createAccount(Account account) {
        database.put(account.getId(), account);
    }

    @Override
    public void updateAccount(Account account) {
        database.put(account.getId(), account);
    }

    @Override
    public void deleteAccount(String id) {
        database.remove(id);
    }
}

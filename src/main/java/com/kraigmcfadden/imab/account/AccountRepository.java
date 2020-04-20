package com.kraigmcfadden.imab.account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> getAccount(String id);
    void createAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(String id);
}

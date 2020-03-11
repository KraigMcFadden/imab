package com.kraigmcfadden.imab.services;

import com.kraigmcfadden.imab.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountManagementService {

    public Account createAccount(String name, double startingCash) {
        return new Account(name, startingCash);
    }
}

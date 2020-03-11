package com.kraigmcfadden.imab.services;

import com.google.common.collect.Maps;
import com.kraigmcfadden.imab.model.Account;
import com.kraigmcfadden.imab.model.Budget;
import com.kraigmcfadden.imab.model.Group;
import com.kraigmcfadden.imab.model.Id;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class PersistenceService {

    private final Map<Id, Account> accounts = Maps.newHashMap();
    private final Map<Id, Group> budgetGroups = Maps.newHashMap();
    private final Map<Id, Budget> budgets = Maps.newHashMap();

    public Optional<Account> getAccountById(Id id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public void saveAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public Optional<Group> getBudgetGroupById(Id id) {
        return Optional.ofNullable(budgetGroups.get(id));
    }

    public void saveBudgetGroup(Group group) {
        budgetGroups.put(group.getId(), group);
    }

    public Optional<Budget> getBudgetById(Id id) {
        return Optional.ofNullable(budgets.get(id));
    }

    public void saveBudget(Budget budget) {
        budgets.put(budget.getId(), budget);
    }
}

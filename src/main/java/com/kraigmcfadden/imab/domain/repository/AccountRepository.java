package com.kraigmcfadden.imab.domain.repository;

import com.kraigmcfadden.imab.domain.model.Account;
import com.kraigmcfadden.imab.domain.model.Id;
import com.kraigmcfadden.imab.persistence.service.PersistenceService;
import com.kraigmcfadden.imab.persistence.model.AccountPO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountRepository extends Repository<Account> {

    private static final Log log = LogFactory.getLog(AccountRepository.class);

    @Autowired
    public AccountRepository(PersistenceService persistenceService) {
        super(persistenceService);
    }

    public Optional<Account> lookup(@NotNull Id id) {
        try {
            AccountPO accountPO = persistenceService.get(id.getId(), AccountPO.class);
            Account account = new Account(id, accountPO.getName(), accountPO.getCash());
            for (String childId : accountPO.getChildIds()) {
                // lookup group by id
                // add to account domain object
            }
            return Optional.of(account);
        } catch (Exception e) {
            log.error("Couldn't get account " + id.getId() + " from data source", e);
            return Optional.empty();
        }
    }

    public void save(@NotNull Account account) {
        try {
            AccountPO accountPO = new AccountPO();
            accountPO.setId(account.getId().getId());
            accountPO.setName(account.getName());
            accountPO.setChildIds(
                    account.getGroups()
                            .stream()
                            .map(g -> g.getId().getId())
                            .collect(Collectors.toList())
            );
            persistenceService.put(accountPO);
        } catch (Exception e) {
            log.error("Couldn't save account " + account.getId().getId() + " to database", e);
        }
    }
}

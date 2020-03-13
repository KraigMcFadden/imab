package com.kraigmcfadden.imab.persistence.service.dynamo.mapper;

import com.kraigmcfadden.imab.persistence.model.AccountPO;
import com.kraigmcfadden.imab.persistence.service.dynamo.DynamoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountDynamoMapper implements DynamoMapper<AccountPO> {

    private final String table;

    @Autowired
    public AccountDynamoMapper(@Value("${aws.dynamo.account.table}") String table) {
        this.table = table;
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public DynamoPersistenceService.DynamoPO toDynamoPO(AccountPO po) {
        return null;
    }

    @Override
    public AccountPO fromDynamoPO(DynamoPersistenceService.DynamoPO po) {
        return null;
    }
}

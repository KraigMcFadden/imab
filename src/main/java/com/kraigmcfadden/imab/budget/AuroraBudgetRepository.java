package com.kraigmcfadden.imab.budget;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Optional;

public class AuroraBudgetRepository implements BudgetRepository {

    private static final Log log = LogFactory.getLog(AuroraBudgetRepository.class);

    @Override
    public Optional<Budget> get(String id) {
        return Optional.empty();
    }

    @Override
    public void create(Budget budget) {

    }

    @Override
    public void update(Budget budget) {

    }

    @Override
    public void delete(String id) {

    }
}

package com.kraigmcfadden.imab.domain.repository;

import com.kraigmcfadden.imab.domain.model.DomainModel;
import com.kraigmcfadden.imab.domain.model.Id;
import com.kraigmcfadden.imab.persistence.service.PersistenceService;

import java.util.Optional;

public abstract class Repository<T extends DomainModel> {

    protected final PersistenceService persistenceService;

    public Repository(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public abstract Optional<T> lookup(Id id);
    public abstract void save(T t);
}

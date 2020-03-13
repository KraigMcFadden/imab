package com.kraigmcfadden.imab.persistence.service;

import com.kraigmcfadden.imab.persistence.model.PO;

public interface PersistenceService {
    <T extends PO> T get(String id, Class<T> clazz) throws Exception;
    void put(PO PO) throws Exception;
}

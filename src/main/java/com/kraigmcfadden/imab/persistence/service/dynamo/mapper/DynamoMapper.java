package com.kraigmcfadden.imab.persistence.service.dynamo.mapper;

import com.kraigmcfadden.imab.persistence.model.PO;
import com.kraigmcfadden.imab.persistence.service.dynamo.DynamoPersistenceService;

public interface DynamoMapper<T extends PO> {

    Class<T> getMappingClass();
    String getTable();
    DynamoPersistenceService.DynamoPO toDynamoPO(T po);
    T fromDynamoPO(DynamoPersistenceService.DynamoPO po);
}

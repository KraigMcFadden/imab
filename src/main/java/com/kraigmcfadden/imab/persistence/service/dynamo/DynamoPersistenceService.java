package com.kraigmcfadden.imab.persistence.service.dynamo;

import com.google.common.collect.Maps;
import com.kraigmcfadden.imab.persistence.model.PO;
import com.kraigmcfadden.imab.persistence.service.PersistenceService;
import com.kraigmcfadden.imab.persistence.service.dynamo.mapper.DynamoMapper;
import org.joda.time.DateTime;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

public class DynamoPersistenceService implements PersistenceService {

    private static final String HASH_KEY = "id";

    private final Map<Class, DynamoMapper> mappers = Maps.newHashMap();

    private final DynamoDbClient dynamo;

    public DynamoPersistenceService(DynamoDbClient dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public <T extends PO> T get(String id, Class<T> clazz) throws Exception {
        DynamoMapper<T> mapper = mappers.get(clazz);
        DynamoPO criteria = new DynamoPO().add(HASH_KEY, id);
        GetItemRequest request = GetItemRequest.builder()
                .tableName(mapper.getTable())
                .key(criteria.po)
                .build();
        GetItemResponse response = dynamo.getItem(request);
        return mapper.fromDynamoPO(new DynamoPO(response.item()));
    }

    @Override
    public void put(PO po) throws Exception {
        DynamoMapper mapper = mappers.get(po.getClass());
        DynamoPO dynamoPO = mapper.toDynamoPO(po);
        PutItemRequest request = PutItemRequest.builder()
                .tableName(mapper.getTable())
                .item(dynamoPO.po)
                .build();
        PutItemResponse response = dynamo.putItem(request);
    }

    public <T extends PO> void addMapper(DynamoMapper<T> mapper) {
        mappers.put(mapper.getMappingClass(), mapper);
    }

    public static class DynamoPO {

        private final Map<String, AttributeValue> po;

        public DynamoPO() {
            po = Maps.newHashMap();
        }

        public DynamoPO(Map<String, AttributeValue> po) {
            this.po = po;
        }

        public DynamoPO add(String columnName, String columnContent) {
            po.put(columnName, AttributeValue.builder().s(columnContent).build());
            return this;
        }

        public DynamoPO add(String columnName, double columnContent) {
            po.put(columnName, AttributeValue.builder().n(String.valueOf(columnContent)).build());
            return this;
        }

        public DynamoPO add(String columnName, long columnContent) {
            po.put(columnName, AttributeValue.builder().n(String.valueOf(columnContent)).build());
            return this;
        }

        public DynamoPO add(String columnName, int columnContent) {
            po.put(columnName, AttributeValue.builder().n(String.valueOf(columnContent)).build());
            return this;
        }

        public DynamoPO add(String columnName, Date columnContent) {
            po.put(columnName, AttributeValue.builder().s(new DateTime(columnContent).toString()).build());
            return this;
        }

        public Optional<String> getString(String columnName) {
            try {
                return Optional.of(po.get(columnName).s());
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        public Optional<Date> getDate(String columnName) {
            try {
                return Optional.of(new DateTime(po.get(columnName).s()).toDate());
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    }
}

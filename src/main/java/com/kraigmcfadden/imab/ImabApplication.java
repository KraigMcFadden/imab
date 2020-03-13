package com.kraigmcfadden.imab;

import com.kraigmcfadden.imab.persistence.service.dynamo.DynamoPersistenceService;
import com.kraigmcfadden.imab.persistence.service.dynamo.mapper.AccountDynamoMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootApplication
public class ImabApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImabApplication.class, args);
    }

    @Bean
    public DynamoPersistenceService dynamoPersistenceService(AccountDynamoMapper accountDynamoMapper) {
        DynamoPersistenceService dynamoPersistenceService = new DynamoPersistenceService(DynamoDbClient.create());
        dynamoPersistenceService.addMapper(accountDynamoMapper);
        return dynamoPersistenceService;
    }
}

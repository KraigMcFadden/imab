package com.kraigmcfadden.imab;

import com.kraigmcfadden.imab.account.AccountRepository;
import com.kraigmcfadden.imab.account.InMemoryAccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "runtime.environment", havingValue = "local", matchIfMissing = true)
public class LocalConfiguration {

    private static final Log log = LogFactory.getLog(LocalConfiguration.class);

    @Bean
    public AccountRepository inMemoryAccountRepository() {
        log.info("Creating in-memory AccountRepository");
        return new InMemoryAccountRepository();
    }
}

package com.kraigmcfadden.imab;

import com.kraigmcfadden.imab.account.AccountRepository;
import com.kraigmcfadden.imab.account.AuroraAccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "runtime.environment", havingValue = "aws")
public class AwsConfiguration {

    private static final Log log = LogFactory.getLog(AwsConfiguration.class);

    @Bean
    public AccountRepository auroraAccountRepository() {
        log.info("Creating Aurora AccountRepository");
        return new AuroraAccountRepository();
    }
}

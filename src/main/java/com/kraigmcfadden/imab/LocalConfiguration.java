package com.kraigmcfadden.imab;

import com.kraigmcfadden.imab.account.AccountRepository;
import com.kraigmcfadden.imab.account.InMemoryAccountRepository;
import com.kraigmcfadden.imab.budget.BudgetRepository;
import com.kraigmcfadden.imab.budget.InMemoryBudgetRepository;
import com.kraigmcfadden.imab.envelope.EnvelopeRepository;
import com.kraigmcfadden.imab.envelope.InMemoryEnvelopeRepository;
import com.kraigmcfadden.imab.expense.ExpenseRepository;
import com.kraigmcfadden.imab.expense.InMemoryExpenseRepository;
import com.kraigmcfadden.imab.timeblock.InMemoryTimeBlockRepository;
import com.kraigmcfadden.imab.timeblock.TimeBlockRepository;
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

    @Bean
    public TimeBlockRepository inMemoryTimeBlockRepository() {
        log.info("Creating in-memory TimeBlockRepository");
        return new InMemoryTimeBlockRepository();
    }

    @Bean
    public BudgetRepository inMemoryBudgetRepository() {
        log.info("Creating in-memory BudgetRepository");
        return new InMemoryBudgetRepository();
    }

    @Bean
    public EnvelopeRepository inMemoryEnvelopeRepository() {
        log.info("Creating in-memory EnvelopeRepository");
        return new InMemoryEnvelopeRepository();
    }

    @Bean
    public ExpenseRepository inMemoryExpenseRepository() {
        log.info("Creating in-memory ExpenseRepository");
        return new InMemoryExpenseRepository();
    }
}

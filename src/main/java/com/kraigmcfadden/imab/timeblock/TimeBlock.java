package com.kraigmcfadden.imab.timeblock;

import com.kraigmcfadden.imab.common.BuilderException;
import software.amazon.awssdk.utils.StringUtils;

import java.time.LocalDate;

public class TimeBlock {

    private final String id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final double openingBalance;
    private final String accountId;

    private TimeBlock(String id,
                      LocalDate startDate,
                      LocalDate endDate,
                     double openingBalance,
                     String accountId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.openingBalance = openingBalance;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private LocalDate startDate;
        private LocalDate endDate;
        private double openingBalance;
        private String accountId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withOpeningBalance(double openingBalance) {
            this.openingBalance = openingBalance;
            return this;
        }

        public Builder withAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public TimeBlock build() {
            if (StringUtils.isBlank(id)) {
                throw new BuilderException("Can't build time block with null id");
            }
            if (startDate == null) {
                throw new BuilderException("Can't build time block with null start date");
            }
            if (endDate == null) {
                throw new BuilderException("Can't build time block with null end date");
            }
            if (StringUtils.isBlank(accountId)) {
                throw new BuilderException("Can't build time block with null account id");
            }

            return new TimeBlock(id, startDate, endDate, openingBalance, accountId);
        }
    }
}

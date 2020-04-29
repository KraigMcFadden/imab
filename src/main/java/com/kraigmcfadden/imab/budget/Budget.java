package com.kraigmcfadden.imab.budget;

import com.kraigmcfadden.imab.common.BuilderException;
import software.amazon.awssdk.utils.StringUtils;

public class Budget {

    private final String id;
    private final double openingBalance;

    private Budget(String id, double openingBalance) {
        this.id = id;
        this.openingBalance = openingBalance;
    }

    public String getId() {
        return id;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private double openingBalance;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withOpeningBalance(double openingBalance) {
            this.openingBalance = openingBalance;
            return this;
        }

        public Budget build() {
            if (StringUtils.isBlank(id)) {
                throw new BuilderException("Can't build budget with null id");
            }
            return new Budget(id, openingBalance);
        }
    }
}

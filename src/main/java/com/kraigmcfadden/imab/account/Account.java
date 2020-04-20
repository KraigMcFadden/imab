package com.kraigmcfadden.imab.account;

import com.kraigmcfadden.imab.common.BuilderException;
import software.amazon.awssdk.utils.StringUtils;

public class Account {

    private final String id;

    private Account(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Account build() {
            if (StringUtils.isBlank(id)) {
                throw new BuilderException("Can't build account with null id");
            }
            return new Account(id);
        }
    }
}

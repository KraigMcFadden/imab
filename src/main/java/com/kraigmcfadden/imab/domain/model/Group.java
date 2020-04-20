package com.kraigmcfadden.imab.domain.model;

import com.google.common.collect.Sets;
import software.amazon.awssdk.utils.StringUtils;

import java.util.Collection;
import java.util.Set;

public class Group {

    private final String id;
    private final String name;
    private final String accountId;
    private final Set<String> childIds;
    private final String timeBlockId;

    private Group(String id,
                  String name,
                  String accountId,
                  Set<String> childIds,
                  String timeBlockId) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
        this.childIds = childIds;
        this.timeBlockId = timeBlockId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public Set<String> getChildIds() {
        return childIds;
    }

    public String getTimeBlockId() {
        return timeBlockId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String accountId;
        private Set<String> childIds;
        private String timeBlockId;

        private Builder() {
            this.childIds = Sets.newHashSet();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder withChildIds(Collection<String> childIds) {
            this.childIds.addAll(childIds);
            return this;
        }

        public Builder withTimeBlockId(String timeBlockId) {
            this.timeBlockId = timeBlockId;
            return this;
        }

        public Group build() {
            if (StringUtils.isBlank(id)) {
                throw new RuntimeException("Can't build account with null id");
            }
            if (StringUtils.isBlank(name)) {
                throw new RuntimeException("Can't build account with null name");
            }
            if (StringUtils.isBlank(accountId)) {
                throw new RuntimeException("Can't build account with null accountId");
            }
            return new Group(id, name, accountId, childIds, timeBlockId);
        }
    }
}

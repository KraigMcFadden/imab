package com.kraigmcfadden.imab.domain.model;

public class Envelope {

    private final String id;
    private final String name;
    private final double allocatedMoney;
    private final String accountId;
    private final String timeBlockId;

    private Envelope(String id,
                     String name,
                     double allocatedMoney,
                     String accountId,
                     String timeBlockId) {
        this.id = id;
        this.name = name;
        this.allocatedMoney = allocatedMoney;
        this.accountId = accountId;
        this.timeBlockId = timeBlockId;
    }
}

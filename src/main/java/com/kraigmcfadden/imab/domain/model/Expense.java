package com.kraigmcfadden.imab.domain.model;

import java.util.Date;

public class Expense {

    private final String id;
    private final String description;
    private final double cost;
    private final Date date;
    private final String accountId;
    private final String envelopeId;
    private final String timeBlockId;

    private Expense(String id,
                    String description,
                    double cost,
                    Date date,
                    String accountId,
                    String envelopeId,
                    String timeBlockId) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.accountId = accountId;
        this.envelopeId = envelopeId;
        this.timeBlockId = timeBlockId;
    }
}

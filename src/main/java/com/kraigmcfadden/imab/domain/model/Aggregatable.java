package com.kraigmcfadden.imab.domain.model;

public interface Aggregatable {

    Id getId();
    double getLimit();
    double getCurrent();
}

package com.kraigmcfadden.imab.envelope;

import com.kraigmcfadden.imab.budget.Budget;

import java.util.Optional;

public interface EnvelopeRepository {

    Optional<Envelope> get(String id);
    void create(Envelope envelope);
    void update(Envelope envelope);
    void delete(String id);
}

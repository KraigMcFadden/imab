package com.kraigmcfadden.imab.envelope;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class InMemoryEnvelopeRepository implements EnvelopeRepository {

    private final Map<String, Envelope> database = Maps.newHashMap();

    @Override
    public Optional<Envelope> get(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void create(Envelope envelope) {
        database.put(envelope.getId(), envelope);
    }

    @Override
    public void update(Envelope envelope) {
        database.put(envelope.getId(), envelope);
    }

    @Override
    public void delete(String id) {
        database.remove(id);
    }
}

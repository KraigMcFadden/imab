package com.kraigmcfadden.imab.envelope;

import java.util.Optional;

public class EnvelopeUpdate {

    private final String envelopeId;
    private String newEnvelopeName;
    private Double newEnvelopeAllocated;

    public EnvelopeUpdate(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public String getEnvelopeId() {
        return envelopeId;
    }

    public Optional<String> getNewEnvelopeName() {
        return Optional.ofNullable(newEnvelopeName);
    }

    public EnvelopeUpdate setNewEnvelopeName(String newEnvelopeName) {
        this.newEnvelopeName = newEnvelopeName;
        return this;
    }

    public Optional<Double> getNewEnvelopeAllocated() {
        return Optional.ofNullable(newEnvelopeAllocated);
    }

    public EnvelopeUpdate setNewEnvelopeAllocated(Double newEnvelopeAllocated) {
        this.newEnvelopeAllocated = newEnvelopeAllocated;
        return this;
    }
}

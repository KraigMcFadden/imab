package com.kraigmcfadden.imab.envelope;

import com.kraigmcfadden.imab.common.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EnvelopeWorker {

    private final EnvelopeRepository envelopeRepository;

    @Autowired
    public EnvelopeWorker(EnvelopeRepository envelopeRepository) {
        this.envelopeRepository = envelopeRepository;
    }

    public Envelope create(String name, double allocated, String budgetId) {
        Envelope envelope = Envelope.newBuilder()
                .withId(UUID.randomUUID().toString())
                .withName(name)
                .withAllocated(allocated)
                .withBudgetId(budgetId)
                .withAccountId("123")
                .build();
        envelopeRepository.create(envelope);
        return envelope;
    }

    public Envelope get(String id) throws NotFoundException {
        Optional<Envelope> envelope = envelopeRepository.get(id);
        return envelope.orElseThrow(() -> { throw new NotFoundException("Envelope with id " + id + " not found in database"); });
    }

    public Envelope update(EnvelopeUpdate envelopeUpdate) {
        Envelope.Builder builder = Envelope.newBuilder()
                .from(get(envelopeUpdate.getEnvelopeId()));
        envelopeUpdate.getNewEnvelopeAllocated().ifPresent(builder::withAllocated);
        envelopeUpdate.getNewEnvelopeName().ifPresent(builder::withName);
        Envelope envelope = builder.build();
        envelopeRepository.update(envelope);
        return envelope;
    }
}

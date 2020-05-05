document.getElementById('envelope-create-submit-button')
    .addEventListener('click', () => {
        const envelopeName = document.getElementById('envelope-name').value;
        const amountAllocated = document.getElementById('envelope-amount-allocated').value;
        const budgetId = document.getElementById('budget-id').value;
        imabClient.createEnvelope(envelopeName, amountAllocated, budgetId)
            .then((envelope) => {
                state.envelopes.push(envelope);
                summary.update();
                breakdown.update();
            });
        closeModal(Modals.ENVELOPE_CREATE);
    });
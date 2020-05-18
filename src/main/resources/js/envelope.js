document.getElementById('envelope-create-submit-button')
    .addEventListener('click', () => {
        const name = document.getElementById('envelope-create-name');
        const allocated = document.getElementById('envelope-create-allocated');
        const budgetId = document.getElementById('envelope-create-budget-id');
        imabClient.createEnvelope(name.value, allocated.value, budgetId.value)
            .then((envelope) => {
                state.envelopes.push(envelope);
                summary.update();
                breakdown.update();
            });
        closeModal(Modals.ENVELOPE_CREATE);
        name.value = null;
        allocated.value = null;
        budgetId.value = null;
    });

document.getElementById('envelope-update-submit-button')
    .addEventListener('click', () => {
            const name = document.getElementById('envelope-update-name');
            const allocated = document.getElementById('envelope-update-allocated');
            const id = document.getElementById('envelope-update-id');
            imabClient.updateEnvelope(id.value, name.value, allocated.value)
                .then((envelope) => {
                    const index = state.envelopes.findIndex((element) => element.id === envelope.id);
                    if (index === -1) {
                        console.error("Did not find a valid envelope in the state to replace, just pushing");
                        state.envelopes.push(envelope);
                    } else {
                        state.envelopes[index] = envelope;
                    }
                    summary.update();
                    breakdown.update();
                });
            closeModal(Modals.ENVELOPE_CREATE);
            name.value = null;
            allocated.value = null;
            id.value = null;
    });
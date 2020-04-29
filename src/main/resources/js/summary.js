const summary = (() => {

    const sectionId = 'summary';
    const cashId = sectionId + '.cash';
    const allocatedId = sectionId + '.allocated';
    const consumedId = sectionId + '.consumed';

    const updateCash = () => {
        document.getElementById(cashId).innerHTML = 'Cash: $' + state.budget.openingBalance;
    };

    const updateAllocated = () => {
        const available = state.budget.openingBalance;
        let allocated = 0;
        for (const envelope of state.envelopes) {
            allocated += envelope.allocated;
        }
        document.getElementById(allocatedId).innerHTML = 'Allocated: $ ' + allocated + ' / ' + available
            + ' (' + (100 * allocated / available) + '%)';
    };

    const updateConsumed = () => {
        const available = state.budget.openingBalance;
        let totalExpense = 0;
        for (const expense of state.expenses) {
            totalExpense += expense.cost;
        }
        document.getElementById(consumedId).innerHTML = 'Consumed: $ ' + totalExpense + ' / ' + available
            + ' (' + (100 * totalExpense / available) + '%)';
    };

    const update = () => {
        updateCash();
        updateAllocated();
        updateConsumed();
    };

    return {
        update: update
    }
})();
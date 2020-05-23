document.getElementById('expense-create-submit-button')
    .addEventListener('click', () => {
        const description = document.getElementById('expense-create-description');
        const cost = document.getElementById('expense-create-cost');
        const date = document.getElementById('expense-create-date');
        const envelopeId = document.getElementById('expense-create-envelope-id');
        const budgetId = document.getElementById('expense-create-budget-id');
        imabClient.createExpense(description.value, cost.value, date.value, envelopeId.value, budgetId.value).then((expense) => {
            state.expenses.push(expense);
            summary.update();
            breakdown.update();
        });
        closeModal(Modals.EXPENSE_CREATE);
        description.value = null;
        cost.value = null;
        date.value = null;
        envelopeId.value = null;
        budgetId.value = null;
    });

document.getElementById('expense-update-submit-button')
    .addEventListener('click', () => {
        const description = document.getElementById('expense-update-description');
        const cost = document.getElementById('expense-update-cost');
        const date = document.getElementById('expense-update-date');
        const envelopeId = document.getElementById('expense-update-envelope-id');
        const id = document.getElementById('expense-update-id');
        imabClient.updateExpense(id.value, description.value, cost.value, date.value, envelopeId.value).then((expense) => {
            const index = state.expenses.findIndex((element) => element.id === expense.id);
            if (index === -1) {
                console.error("Did not find a valid expense in the state to replace, just pushing");
                state.expenses.push(expense);
            } else {
                state.expenses[index] = expense;
            }
            summary.update();
            breakdown.update();
        });
        closeModal(Modals.EXPENSE_UPDATE);
        description.value = null;
        cost.value = null;
        date.value = null;
        envelopeId.value = null;
        id.value = null;
    });
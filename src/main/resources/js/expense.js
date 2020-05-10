document.getElementById('expense-create-submit-button')
    .addEventListener('click', () => {
        const description = document.getElementById('expense-description').value;
        const cost = document.getElementById('expense-cost').value;
        const date = document.getElementById('expense-date').value;
        const envelopeId = document.getElementById('expense-envelope-id').value;
        const budgetId = document.getElementById('expense-budget-id').value;
        imabClient.createExpense(description, cost, date, envelopeId, budgetId).then((expense) => {
            state.expenses.push(expense);
            summary.update();
            breakdown.update();
        });
        closeModal(Modals.EXPENSE_CREATE);
    });
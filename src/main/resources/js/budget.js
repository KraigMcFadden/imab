document.getElementById('budget-create-submit-button')
    .addEventListener('click', () => {
        const openingBalance = document.getElementById('budget-opening-balance').value;
        imabClient.createBudget(openingBalance).then((budget) => {
            state.budget = budget;
            summary.update();
            breakdown.update();
        });
        closeModal(Modals.BUDGET_CREATE);
        openingBalance.value = null;
    });
document.getElementById('budget-create-submit-button')
    .addEventListener('click', () => {
        const openingBalance = document.getElementById('budget-opening-balance').value;
        createBudget(openingBalance).then((budget) => {
            summary.update();
            breakdown.update();
        });
        closeModal(modals.budgetCreateModal);
    });
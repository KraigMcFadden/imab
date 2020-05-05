const Modals = {
    BUDGET_CREATE: {
        id: 'budget-create-modal',
        closeSpanId: 'close-budget-create-modal'
    },
    ENVELOPE_CREATE: {
        id: 'envelope-create-modal',
        closeSpanId: 'close-envelope-create-modal'
    },
    EXPENSE_CREATE: {
        id: 'expense-create-modal',
        closeSpanId: 'close-expense-create-modal'
    }
};

function setupModal(modalEnum) {
    console.log('Setting up modal ' + modalEnum.id);

    const modal = document.getElementById(modalEnum.id);
    const closeSpan = document.getElementById(modalEnum.closeSpanId);

    // When the user clicks on <span> (x), close the modal
    closeSpan.addEventListener('click',() => modal.style.display = "none");

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}

function registerOpenButtonForModal(modalEnum, openButtonId) {
    console.log('Registering open button ' + openButtonId + ' for modal ' + modalEnum.id);

    const modal = document.getElementById(modalEnum.id);
    const openButton = document.getElementById(openButtonId);

    // When the user clicks on the button, open the modal
    openButton.addEventListener('click', () => modal.style.display = "block");
}

function closeModal(modalEnum) {
    const modal = document.getElementById(modalEnum.id);
    modal.style.display = "none";
}

setupModal(Modals.BUDGET_CREATE);
setupModal(Modals.ENVELOPE_CREATE);
setupModal(Modals.EXPENSE_CREATE);
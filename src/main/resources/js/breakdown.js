const breakdown = (() => {

    const SECTION_ID = 'breakdown';
    const ENVELOPE_COOLORS = ['#D62839', '#004BA8', '#00916E', '#3F88C5', '#E28413'];
    const BUTTON_COOLORS = ['#5A0406', '#00085A', '#00250B', '#061547', '#4E0D02'];

    const load = () => {

    };

    const getBudget = () => {
        return state.budget;
    };

    const getEnvelopesForBudget = (budget) => {
        const budgetId = budget.id;
        const envelopes = [];
        for (const envelope of state.envelopes) {
            if (envelope.budgetId === budgetId) {
                envelopes.push(envelope);
            }
        }
        return envelopes;
    };

    const getExpensesForEnvelopes = (envelopes) => {
        const expenses = {};
        for (const envelope of envelopes) {
            expenses[envelope.id] = [];
            for (const expense of state.expenses) {
                if (expense.envelopeId === envelope.id) {
                    expenses[envelope.id].push(expense);
                }
            }
        }
        return expenses;
    };

    const createHtml = () => {
        const section = document.getElementById(SECTION_ID);

        const budget = getBudget();

        /*
        <button>Create Budget</button>
         */
        if (!budget) {
            const addBudgetButton = newButton('add-budget-button', 'Create Budget', section);
            registerOpenButtonForModal(Modals.BUDGET_CREATE, addBudgetButton.id);
            return;
        }

        /*
        <div id="envelope-..." class="envelope">
            envelope name
            <div id="expense-..." class="expense">
                expense description - $...
            </div>
            <button>Add Expense</button>
        </div>
        <button>Add Envelope</button>
         */
        let coolorsIndex = 0;
        const envelopes = getEnvelopesForBudget(budget);
        const expenses = getExpensesForEnvelopes(envelopes);
        for (const envelope of envelopes) {
            const envelopeDiv = createElement(section, 'div', {
                id: 'envelope-' + envelope.id,
                className: 'envelope',
            });
            registerOpenButtonForModal(Modals.ENVELOPE_UPDATE, envelopeDiv.id);
            const envelopeHeader = createElement(envelopeDiv, 'div', {
                className: 'envelope-header',
                text: envelope.name + ' - ' + toMoneyStr(envelope.allocated)
            });
            const envelopeContent = createElement(envelopeDiv, 'div', {
                className: 'envelope-content'
            });

            for (const expense of expenses[envelope.id]) {
                const expenseDiv = createElement(envelopeContent, 'div', {
                    id: 'expense-' + expense.id,
                    className: 'expense'
                });
                registerOpenButtonForModal(Modals.EXPENSE_UPDATE, expenseDiv.id);
                const expenseHeader = createElement(expenseDiv, 'div', {
                    className: 'expense-header',
                    text: toMoneyStr(expense.cost)
                });
                const expenseContent = createElement(expenseDiv, 'div', {
                    className: 'expense-content',
                    text: expense.description
                });
            }

            const addExpenseButton = newButton(envelopeDiv.id + '-add-expense-button', 'Add Expense', envelopeContent);
            registerOpenButtonForModal(Modals.EXPENSE_CREATE, addExpenseButton.id);
            addExpenseButton.addEventListener('click', () => {
                setHiddenInput('expense-create-envelope-id', envelope.id);
                setHiddenInput('expense-create-budget-id', envelope.id);
            });

            // set the envelope color
            envelopeDiv.style.color = 'white';
            envelopeDiv.style.backgroundColor = ENVELOPE_COOLORS[coolorsIndex];
            addExpenseButton.style.backgroundColor = BUTTON_COOLORS[coolorsIndex++];
            if (coolorsIndex >= ENVELOPE_COOLORS.length) {
                coolorsIndex = 0;
            }
        }

        const addEnvelopeButton = newButton('add-envelope-button', 'Add Envelope', section);
        registerOpenButtonForModal(Modals.ENVELOPE_CREATE, addEnvelopeButton.id);
        addEnvelopeButton.addEventListener('click', () => {
            setHiddenInput('envelope-create-budget-id', budget.id);
        });
    };

    const update = () => {
        clearAllChildElements(SECTION_ID);
        createHtml();
    };

    return {
        update: update
    }
})();
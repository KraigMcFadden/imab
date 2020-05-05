const breakdown = (() => {

    const sectionId = 'breakdown';

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
        const section = document.getElementById(sectionId);

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
        <div id="envelope-...">
            envelope name
            <div id="expense-...">
                expense description - $...
            </div>
            <button>Add Expense</button>
        </div>
        <button>Add Envelope</button>
         */
        const envelopes = getEnvelopesForBudget(budget);
        const expenses = getExpensesForEnvelopes(envelopes);
        for (const envelope of envelopes) {
            const envelopeDiv = document.createElement('div');
            envelopeDiv.id = 'envelope-' + envelope.id;
            section.appendChild(envelopeDiv);

            const envelopeDivName = document.createTextNode(envelope.name);
            envelopeDiv.appendChild(envelopeDivName);

            for (const expense of expenses[envelope.id]) {
                const expenseDiv = document.createElement('div');
                expenseDiv.id = 'expense-' + expense.id;

                const expenseDivDetail = document.createTextNode(expense.description + ' - $' + expense.cost);
                expenseDiv.appendChild(expenseDivDetail);

                envelopeDiv.appendChild(expenseDiv);
            }

            const addExpenseButton = newButton(envelopeDiv.id + '-add-expense-button', 'Add Expense', envelopeDiv);
            registerOpenButtonForModal(Modals.EXPENSE_CREATE, addExpenseButton.id);
            addExpenseButton.addEventListener('click', () => {
                setHiddenInput('envelope-id', envelope.id);
            });
        }

        const addEnvelopeButton = newButton('add-envelope-button', 'Add Envelope', section);
        registerOpenButtonForModal(Modals.ENVELOPE_CREATE, addEnvelopeButton.id);
        addEnvelopeButton.addEventListener('click', () => {
            setHiddenInput('budget-id', budget.id);
        });
    };

    const clearAllChildren = () => {
        const section = document.getElementById(sectionId);
        while (section.firstChild) {
            section.removeChild(section.firstChild);
        }
    };

    const update = () => {
        clearAllChildren();
        createHtml();
    };

    return {
        update: update
    }
})();
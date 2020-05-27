const breakdown = (() => {

    const SECTION_ID = 'breakdown';

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
        const envelopes = getEnvelopesForBudget(budget);
        const expenses = getExpensesForEnvelopes(envelopes);
        for (const envelope of envelopes) {
            const envelopeDiv = createElement(section, 'div', {
                id: 'envelope-' + envelope.id,
                className: 'envelope',
            });

            // Envelope update listener
            // registerOpenButtonForModal(Modals.ENVELOPE_UPDATE, envelopeDiv.id);
            // envelopeDiv.addEventListener('click', () => {
            //     setHiddenInput('envelope-update-id', envelope.id);
            // });

            const envelopeHeader = createElement(envelopeDiv, 'h3', {
                className: 'envelope-header',
                text: envelope.name
            });
            const envelopeHeaderDollarFigure = createElement(envelopeHeader, 'span', {
                className: 'monetary',
                text: toMoneyStr(envelope.allocated)
            })

            const expensesDiv = createElement(envelopeDiv, 'div', {
                className: 'expenses'
            });

            envelopeDiv.addEventListener('click', () => {
                if (expensesDiv.style.display !== 'block') {
                    envelopeDiv.style.backgroundColor = "midnightblue";
                    expensesDiv.style.display = "block";
                } else {
                    envelopeDiv.style.backgroundColor = "black";
                    expensesDiv.style.display = "none";
                }
            });

            for (const expense of expenses[envelope.id]) {
                const expenseP = createElement(expensesDiv, 'p', {
                    id: 'expense-' + expense.id,
                    className: 'expense',
                    text: expense.description
                });
                const expensePDollarFigure = createElement(expenseP, 'span', {
                    text: toMoneyStr(expense.cost)
                });

                // Expense update listener
                // registerOpenButtonForModal(Modals.EXPENSE_UPDATE, expenseDiv.id);
                // expenseDiv.addEventListener('click', () => {
                //     setHiddenInput('expense-update-id', expense.id);
                //     setHiddenInput('expense-update-envelope-id', envelope.id);
                // });
            }

            const addExpenseButton = createElement(expensesDiv, 'button', {
                id: envelopeDiv.id + '-add-expense-button',
                text: '+',
                className: 'round add-expense-button'
            });
            registerOpenButtonForModal(Modals.EXPENSE_CREATE, addExpenseButton.id);
            addExpenseButton.addEventListener('click', () => {
                setHiddenInput('expense-create-envelope-id', envelope.id);
                setHiddenInput('expense-create-budget-id', envelope.id);
            });
        }

        const addEnvelopeButton = createElement(section, 'button', {
            id: budget.id + '-add-envelope-button',
            text: '+',
            className: 'round add-envelope-button'
        });
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
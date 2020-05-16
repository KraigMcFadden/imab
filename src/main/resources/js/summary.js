const summary = (() => {

    const SECTION_ID = 'summary';
    const SUMMARY_LINE_ITEM_CLASS = 'summary-line-item';
    const SUMMARY_HEADER_CLASS = 'summary-header';
    const SUMMARY_CONTENT_CLASS = 'summary-content';

    const createLineItem = (id, header, content) => {
        const section = document.getElementById(SECTION_ID);
        const lineItem = createElement(section, 'div', {
            id: id,
            className: SUMMARY_LINE_ITEM_CLASS
        });
        createElement(lineItem, 'div', {
            className: SUMMARY_HEADER_CLASS,
            text: header
        });
        createElement(lineItem, 'div', {
            className: SUMMARY_CONTENT_CLASS,
            text: content
        })
    }

    const createCash = () => {
        createLineItem(
            SECTION_ID + '.cash',
            'Cash:',
            toMoneyStr(state.budget.openingBalance)
        );
    };

    const createAllocated = () => {
        const available = state.budget.openingBalance;
        let allocated = 0;
        for (const envelope of state.envelopes) {
            allocated += envelope.allocated;
        }
        createLineItem(
            SECTION_ID + '.allocated',
            'Allocated:',
            toMoneyStr(allocated) + ' / ' + toMoneyStr(available) + ' (' + Math.round(100 * allocated / available) + '%)'
        );
    };

    const createConsumed = () => {
        const available = state.budget.openingBalance;
        let totalExpense = 0;
        for (const expense of state.expenses) {
            totalExpense += expense.cost;
        }
        createLineItem(
            SECTION_ID + '.consumed',
            'Consumed ($):',
            toMoneyStr(totalExpense) + ' / ' + toMoneyStr(available) + ' (' + Math.round(100 * totalExpense / available) + '%)'
        );
    };

    const update = () => {
        clearAllChildElements(SECTION_ID);
        createCash();
        createAllocated();
        createConsumed();
    };

    return {
        update: update
    }
})();
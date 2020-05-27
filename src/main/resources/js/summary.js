const summary = (() => {

    const SECTION_ID = 'summary';
    const SUMMARY_LINE_ITEM_CLASS = 'summary-line-item';
    const SUMMARY_HEADER_CLASS = 'summary-header';
    const SUMMARY_CONTENT_CLASS = 'summary-content';

    const section = document.getElementById(SECTION_ID);

    const createLineItem = (id, header, content, width, bgc) => {
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
            text: content,
            style: {
                width: width,
                backgroundColor: bgc
            }
        })
    }

    const createCash = () => {
        createElement(section, 'h2', { text: 'Cash:' });
        createElement(section, 'h1', {
            text: toMoneyStr(state.budget.openingBalance)
        });
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
            toMoneyStr(allocated),
            (Math.round(100 * allocated / available)) + '%',
            allocated > available ? 'red' : 'midnightblue'
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
            'Consumed:',
            toMoneyStr(totalExpense),
            (Math.round(100 * totalExpense / available)) + '%',
            totalExpense > available ? 'red' : 'midnightblue'
        );
    };

    const update = () => {
        clearAllChildElements(SECTION_ID);
        if (state.budget) {
            createCash();
            createAllocated();
            createConsumed();
        }
    };

    return {
        update: update
    }
})();
const imabClient = (() => {

    const createAccount = async function () {
        const location = await restTemplate.post('/accounts');
        return await restTemplate.get(location);
    };

    const getAccount = async function (accountId) {
        return await restTemplate.get('/accounts/' + accountId);
    };

    const createTimeBlock = async function (accountId, startDate, endDate, openingBalance) {
        console.log('Creating time block with args: ' + accountId + ', ' + startDate + ', ' + endDate + ', ' + openingBalance);
        const location = await restTemplate.post(
            '/accounts/' + accountId + '/timeblocks',
            {
                startDate: startDate,
                endDate: endDate,
                openingBalance: openingBalance
            }
        );
        return await restTemplate.get(location);
    };

    const getAllTimeBlocksForAccount = async function (accountId) {
        const timeblocks = await restTemplate.get('/accounts/' + accountId + '/timeblocks');
        console.log("Retrieved timeblocks: " + JSON.stringify(timeblocks));
        return timeblocks;
    };

    const createBudget = async function (openingBalance) {
        console.log('Creating budget with opening balance: ' + openingBalance);
        const location = await restTemplate.post('/budgets',
            {
                openingBalance: openingBalance
            }
        );
        return await restTemplate.get(location);
    };

    const createEnvelope = async function (name, allocated, budgetId) {
        console.log('Creating envelope with name: ' + name + ', allocated: ' + allocated + ', and budget id: ' + budgetId);
        const location = await restTemplate.post('/envelopes',
            {
                name: name,
                allocated: allocated,
                budgetId: budgetId
            }
        );
        return await restTemplate.get(location);
    };

    const updateEnvelope = async function (name, allocated, budgetId) {
        console.log('Updating envelope with name: ' + name + ', allocated: ' + allocated + ' and budget id: ' + budgetId);

    }

    const createExpense = async function (description, cost, date, envelopeId, budgetId) {
        console.log('Creating expense with description: ' + description + ', cost: ' + cost + ', date: ' + date + ', envelopeId: ' + envelopeId + ', budgetId: ' + budgetId);
        const location = await restTemplate.post('/expenses',
            {
                description: description,
                cost: cost,
                date: date,
                envelopeId: envelopeId,
                budgetId: budgetId
            }
        );
        return await restTemplate.get(location);
    };

    return {
        createBudget: createBudget,
        createEnvelope: createEnvelope,
        createExpense: createExpense
    }
})();
const imabClient = (() => {

    const Resource = {
        ACCOUNTS: '/accounts',
        BUDGETS: '/budgets',
        ENVELOPES: '/envelopes',
        EXPENSES: '/expenses'
    };

    const join = function (...pathVars) {
        return pathVars.reduce((prev, current) => {
            return prev + '/' + current;
        });
    };

    const createAccount = async function () {
        const location = await restTemplate.post(Resource.ACCOUNTS);
        return await restTemplate.get(location);
    };

    const getAccount = async function (accountId) {
        return await restTemplate.get(join(Resource.ACCOUNTS, accountId));
    };

    const createTimeBlock = async function (accountId, startDate, endDate, openingBalance) {
        console.log('Creating time block with args: ' + accountId + ', ' + startDate + ', ' + endDate + ', ' + openingBalance);
        const location = await restTemplate.post(
            join(Resource.ACCOUNTS, accountId, 'timeblocks'),
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
        const location = await restTemplate.post(Resource.BUDGETS,
            {
                openingBalance: openingBalance
            }
        );
        return await restTemplate.get(location);
    };

    const createEnvelope = async function (name, allocated, budgetId) {
        console.log('Creating envelope with name: ' + name + ', allocated: ' + allocated + ', and budget id: ' + budgetId);
        const location = await restTemplate.post(Resource.ENVELOPES,
            {
                name: name,
                allocated: allocated,
                budgetId: budgetId
            }
        );
        return await restTemplate.get(location);
    };

    const updateEnvelope = async function (envelopeId, name, allocated) {
        console.log('Updating envelope ' + envelopeId + ' with name: ' + name + ' and allocated: ' + allocated);
        return await restTemplate.put(join(Resource.ENVELOPES, envelopeId), {
            name: name,
            allocated: allocated
        });
    }

    const createExpense = async function (description, cost, date, envelopeId, budgetId) {
        console.log('Creating expense with description: ' + description + ', cost: ' + cost + ', date: ' + date + ', envelopeId: ' + envelopeId + ', budgetId: ' + budgetId);
        const location = await restTemplate.post(Resource.EXPENSES,
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

    const updateExpense = async function (expenseId, description, cost, date, envelopeId) {
        console.log('Updating expense ' + expenseId + ' with description: ' + description + ', cost: ' + cost + ', date: ' + date + ' and envelopeId: ' + envelopeId);
        return await restTemplate.put(join(Resource.EXPENSES, expenseId), {
            description: description,
            cost: cost,
            date: date,
            envelopeId: envelopeId
        });
    }

    return {
        createBudget: createBudget,
        createEnvelope: createEnvelope,
        updateEnvelope: updateEnvelope,
        createExpense: createExpense,
        updateExpense: updateExpense
    }
})();
async function createPage() {

    // set up account
    const accountDiv = document.createElement('div');
    const accountCreationResponse = await post('/accounts', { name: 'My Account' });
    const accountId = accountCreationResponse.id;
    const account = await get('/accounts/' + accountId);

    accountDiv.id = accountId;
    const accountDivContents = document.createTextNode('Name: ' + account.name);
    accountDiv.appendChild(accountDivContents);

    // groups
    const groupsList = await get('/accounts/' + accountId + '/groups');
    for (const group of groupsList) {
        const groupDiv = document.createElement('div');
        const groupId = group.id.id;
        groupDiv.id = groupId;
        const groupDivContents = document.createTextNode('Name: ' + group.name);
        groupDiv.appendChild(groupDivContents);

        // budgets for each group
        const budgetsList = await get('/accounts/' + accountId + '/groups/' + groupId + '/budgets');
        for (const budget of budgetsList) {
            const budgetDiv = document.createElement('div');
            const budgetId = budget.id.id;
            budgetDiv.id = budgetId;
            const budgetDivContents = document.createTextNode('Name: ' + budget.name);
            budgetDiv.appendChild(budgetDivContents);

            groupDiv.appendChild(budgetDiv);
        }

        accountDiv.appendChild(groupDiv);
    }

    document.body.appendChild(accountDiv);
}
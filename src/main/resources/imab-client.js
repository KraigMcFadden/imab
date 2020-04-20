async function createAccount() {
    const location = await post('/accounts');
    return await get(location);
}

async function getAccount(accountId) {
    return await get('/accounts/' + accountId);
}

async function createTimeBlock(accountId, startDate, endDate, openingBalance) {
    console.log('Creating time block with args: ' + accountId + ', ' + startDate + ', ' + endDate + ', ' + openingBalance);
    const location = await post(
        '/accounts/' + accountId + '/timeblocks',
        {
            startDate: startDate,
            endDate: endDate,
            openingBalance: openingBalance
        }
    );
    return await get(location);
}

async function getAllTimeBlocksForAccount(accountId) {
    return await get('/accounts/' + accountId + '/timeblocks');
}
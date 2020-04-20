function submitCreateTimeblockForm(accountId) {
    const startDateInput = document.getElementById('startDate');
    const endDateInput = document.getElementById('endDate');
    const openingBalanceInput = document.getElementById('openingBalance');
    createTimeBlock(accountId, startDateInput.value, endDateInput.value, openingBalanceInput.value)
        .then((timeblock) => createPage(accountId, timeblock.id));
}
async function createAccount() {
    const location = await post('/accounts');
    return await get(location);
}
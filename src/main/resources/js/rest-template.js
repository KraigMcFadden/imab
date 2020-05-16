// EXAMPLE
// async function post(url = '', data = {}) {
//     // Default options are marked with *
//     const response = await fetch(url, {
//         method: 'POST', // *GET, POST, PUT, DELETE, etc.
//         mode: 'cors', // no-cors, *cors, same-origin
//         cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
//         credentials: 'same-origin', // include, *same-origin, omit
//         headers: {
//             'Content-Type': 'application/json'
//             // 'Content-Type': 'application/x-www-form-urlencoded',
//         },
//         redirect: 'follow', // manual, *follow, error
//         referrerPolicy: 'no-referrer', // no-referrer, *client
//         body: JSON.stringify(data) // body data type must match "Content-Type" header
//     });
//     return await response.json(); // parses JSON response into native JavaScript objects
// }

const restTemplate = (() => {

    const post = async function (url = '', data = {}) {
        const body = JSON.stringify(data);
        console.log('POSTing with body: ' + body);

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
        });
        return response.headers.get('location');
    };

    const get = async function (url = '') {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json'
            },
        });
        return await response.json();
    };

    const put = async function (url = '', data = {}) {
        const body = JSON.stringify(data);
        console.log('PUTting with body: ' + body);

        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
        });
        return response.json();
    }

    return {
        get: get,
        post: post,
        put: put
    }
})();
function $(selector) {
    return document.querySelector(selector);
}

function fetchManager({ url, method, body, headers, onSuccess, onFailed }) {
    fetch(url, {method,body,headers,credentials: "same-origin"})
        .then((response) => {
        if(response.status >= 400) {
            onFailed(response.status)
        }
        return response.json()
    }).then((result) => {
        onSuccess(result)
    })
}
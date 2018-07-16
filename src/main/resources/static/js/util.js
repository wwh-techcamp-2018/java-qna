function $(selector) {
    return document.querySelector(selector);
}

function fetchManager({ url, method, body, headers, callback }) {
    fetch(url, {
        method,
        body,
        headers,
 	credentials: "same-origin"
    }).then((response) => {
        return response.json()
    }).then((result) => {
        callback(url, result);
    }).catch((error) => {
        alert('에러가 발생했습니다.');
    });
}

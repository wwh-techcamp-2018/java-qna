
function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    const answerBtn = $(".submit-write .btn");
    const answer = $(".qna-comment-slipp-articles");
    answerBtn.addEventListener("click", registerAnswerHandler);
    answer.addEventListener("click", deleteAnswerHandler);
}

function fetchManager({ url, method, body, headers, callback }) {
    fetch(url, {method,body,headers,credentials: "same-origin"})
        .then((response) => {
        return response.json()
    }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".submit-write textarea").value;
    $(".submit-write textarea").value = "";

    var qna_id = $('#submitBtn').getAttribute("qna-id");
    fetchManager({
        url: '/api/questions/' + qna_id + '/answers',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    if(evt.target.className !== "delete-answer-button") return;
    evt.preventDefault();
    var qna_id = evt.target.getAttribute("qna-id");
    var answer_id = evt.target.getAttribute("answer-id");
    fetchManager({
        url: '/api/questions/' + qna_id + '/answers/' + answer_id,
        method: 'DELETE',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({answer_id}),
        callback: deleteAnswer
    })
}

function appendAnswer({id, contents, question, writer}) {
    const html = `
        <article class="article" answer-id="${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">2018.09.01</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${contents}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/questions/${question.id}/answers/${id}">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form" id="deleteBtn">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="delete-answer-button"  qna-id="${question.id}" answer-id="${id}">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    $(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}

function deleteAnswer({answerId, message, success}) {
    if (success !== true) {
        alert(message);
        return;
    }
    const selector = `.article[answer-id='${answerId}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}
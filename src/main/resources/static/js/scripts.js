String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

function $(selector) {
    return document.querySelector(selector);
}

function $$(selector) {
    return document.querySelectorAll(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    const answerBtn = $("#add-answer");
    if (answerBtn != null)
        answerBtn.addEventListener("click", registerAnswerHandler);

    const deleteBtnList = $$(".delete-answer-button");
    if (deleteBtnList != null)
        for (var i = 0; i < deleteBtnList.length; i++)
            deleteBtnList[i].addEventListener("click", registerDeleteHandler);
}

function fetchManager({url, method, body, headers, callback}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then((response) => {
            return response.json()
        }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(evt) {
    evt.preventDefault();

    var questionId = $(".question-id").value;
    if (questionId === null) return;
    const content = $("#answer-content").value;
    $("#answer-content").value = "";

    fetchManager({
        url: '/api/questions/' + questionId + '/answers',
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({content}),
        callback: appendAnswer
    })
}

function appendAnswer({id, content, question, writer, formattedDate}) {
    const html = `
        <article class="article" id="answer-${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">${formattedDate}</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${content}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/questions/${question.id}/answers/${id}">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form">                                          
                        <button type="submit" class="delete-answer-button" value="${id}">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    $(".qna-comment-slipp-articles").insertAdjacentHTML("beforeEnd", html);
    const deleteBtnList = $$(".delete-answer-button");
    if (deleteBtnList != null)
        deleteBtnList[deleteBtnList.length - 1].addEventListener("click", registerDeleteHandler);
}

function registerDeleteHandler(evt) {
    evt.preventDefault();

    var questionId = $(".question-id").value;
    if (questionId === null) return;
    var answerId = evt.srcElement.value;
    if (answerId === null) return;

    deleteManager({
        url: '/api/questions/' + questionId + '/answers/' + answerId,
        headers: {'content-type': 'application/json'},
        method: 'DELETE',
        callback: deleteAnswer
    })
}

function deleteManager({url, headers, method, callback}) {
    fetch(url, {method, headers, credentials: "same-origin"})
        .then((response) => {
            console.log(response)
            return response.json();
        }).then((result) => {
        callback(result);
    })
}

function deleteAnswer(result) {
    var id = result.answerId;
    $("#answer-" + id).remove();
}

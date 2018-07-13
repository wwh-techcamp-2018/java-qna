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
        callback(result)
    })
}

function appendAnswer({contents, writer, id,question}) {
    const html = `
        <article class="article" data-id=${id}>
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                         class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="/users/1/자바지기" class="article-author-name">${writer.name}</a>
                    <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                        2016-01-12 14:06
                    </a>
                </div>
            </div>
            <div class="article-doc comment-doc">
                <p>${contents}</p>
            </div>
            <div class="article-util">
                <ul class="article-util-list">
                    <li>
                        <a class="link-modify-article"
                           href="/questions/413/answers/1405/form">수정</a>
                    </li>
                    <li>
                        <a class="delete-answer-button" href="/api/questions/${question.id}/answers/${id}">삭제
                        </a>
                    </li>
                </ul>
            </div>
        </article>`
    $(".qna-comment-slipp-articles").insertAdjacentHTML("beforeend", html);

    const answer = $(".delete-answer-button");
    answer.addEventListener("click", deleteAnswerHandler);
}

function deleteAnswer({id}) {
    const selector = `.article[data-id='${id}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $("#answerContent").value;
    $("#answerContent").value = "";

    const questionId = $('#questionId').value;
    fetchManager({
        url: `/api/questions/${questionId}/answers`,
        method: 'POST',
        headers: { 'content-type': 'application/json; charset=utf-8' },
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    if(evt.target.className !== "delete-answer-button") return;
    evt.preventDefault();
    const url = evt.target.href;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({id}),
        callback: deleteAnswer
    })
}

function initEvents() {
    const answerBtn = $("#submitAnswer");
    answerBtn.addEventListener("click", registerAnswerHandler);
    const answer = $(".delete-answer-button");
    answer.addEventListener("click", deleteAnswerHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})


String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
function appendAnswer({contents, writer, date, id, question}) {
    const html = `
    <article class="article answer" id="answer-${id}" data-id='${id}'>
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                     class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="/users/${writer.id}/${writer.name}" class="article-author-name">${writer.name}</a>
                <a href="#answer-${id}" class="article-header-time" title="퍼머링크">
                    ${date}
                </a>
            </div>
        </div>
        <div class="article-doc comment-doc">
            <p>${contents}</p>
        </div>
        <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/questions/${question.id}/answers/${id}/form">수정</a>
                </li>
                <li>
                    <a class="delete-answer-button" href="/api/questions/${question.id}/answers/${id}">삭제</a>
                </li>
            </ul>
        </div>
    </article>
    `

    $(".qna-comment-slipp-articles")[0].insertAdjacentHTML("afterbegin", html);
}

    function deleteAnswer({answerid}) {
    const selector = `.answer[data-id='${answerid}']`;
    const target = $(selector)[0];
    target.parentNode.removeChild(target);
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".form-control")[1].value;
    $(".form-control")[1].value = "";
    const qid = evt.target.getAttribute("data-qid");

    fetchManager({
        url: `/api/questions/${qid}/answers`,
        method: 'POST',
        headers: { 'content-type': 'application/json'},
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
    const answerBtn = $(".answer-form .btn")[0];
    const answer = $(".qna-comment-slipp-articles")[0];
    answerBtn.addEventListener("click", registerAnswerHandler);
    answer.addEventListener("click", deleteAnswerHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})
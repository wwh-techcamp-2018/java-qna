document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    const answerDeleteBtns = $('.qna-comment-slipp-articles')
    const answerBtn = $(".submit-write .btn");
    if(answerBtn === null) return;
    answerBtn.addEventListener("click", registerAnswerHandler);
    answerDeleteBtns.addEventListener("click", deleteAnswerHandler);
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    let textarea = $(".submit-write textarea");
    const contents = textarea.value;
    textarea.value = "";
    const url = $(".submit-write").action;

    fetchManager({
        url,
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        onSuccess: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    if(evt.target.className !== "delete-answer-button") return;
    evt.preventDefault();
    const url = evt.target.parentNode.action;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        onSuccess: deleteAnswer,
        onFailed: deleteFailed
    })
}

function deleteFailed(status) {
    if(status == 401) {
        alert("You are not logged in")
        return;
    }
    if (status == 403) {
        alert("Permission denied")
    }
}

function deleteAnswer({answerId}) {
    const selector = `.article[data-id='${answerId}']`;
    const target = $(selector);
    target.parentNode.removeChild(target);
}

function appendAnswer({id, contents, question, writer, createdDate}) {
    const html = `
        <article class="article" data-id='${id}'>
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">${createdDate}</div>
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
                    <form class="delete-answer-form" action="/api/questions/${question.id}/answers/${id}" method="POST">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="submit" class="delete-answer-button">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    $(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}
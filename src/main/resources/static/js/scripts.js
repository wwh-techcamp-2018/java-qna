String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    // 답변하기
    const answerBtn = $(".submit-write .btn");
    if(answerBtn === null) return;
    answerBtn.addEventListener("click", registerAnswerHandler);

    // 삭제하기 이벤트 위임
    const commentParent = $(".qna-comment-slipp-articles");
    if (commentParent === null) return;
    commentParent.addEventListener("click", deleteAnswerHandler);
}

function fetchManager({ url, method, body, headers, callback }) {
    console.log(url);
    fetch(url, {method,body,headers,credentials: "same-origin"})
        .then((response) => {
        return response.json()
    }).then((result) => {
        callback(result)
    })
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".submit-write .form-group .form-control").value;
    $(".submit-write .form-group .form-control").value = "";

    fetchManager({
        url: '/api/questions/' + evt.target.getAttribute("data-question-id") + '/answers',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    evt.preventDefault();
    if (evt.target && evt.target.className == "delete-answer-button") {
        fetchManager({
            url: '/api/questions/' + evt.target.getAttribute("data-question-id")
            + '/answers/' + evt.target.getAttribute("data-answer-id"),
            method: 'DELETE',
            headers: { 'content-type': 'application/json'},
            callback: deleteAnswer
        })
    }
}

function closest() {
    
}

function appendAnswer({id, contents, question, writer}) {
    const html = `
        <article class="article" data-question-id="${question.id}" data-answer-id="${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="#" class="article-author-name">${writer.userId}</a>
                    <div class="article-header-time"></div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${contents}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form">
                        <button type="submit" class="delete-answer-button" data-question-id="${question.id}" data-answer-id="${id}">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `

    $(".qna-comment-slipp-articles").insertAdjacentHTML("beforeend", html);
}


function deleteAnswer({id, deleted}) {
    if (!deleted) {
        alert("삭제 권한이 없습니다.");
        return;
    }
    const seletor = `.article[data-answer-id='${id}']`;
    const target = $(seletor);
    target.parentNode.removeChild(target);

}
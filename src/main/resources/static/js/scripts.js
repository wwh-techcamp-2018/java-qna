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
    }).catch( (e) => {
        console.log(e);
    })
}

function appendAnswer({message, answer}) {
    if (message !== "ok") {
        alert(message);
        return;
    }

    const html =`
    <article class="article" id="answer-${answer.id}">
        <div class="article-header">
            <div class="article-header-thumb">
                <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
            </div>
            <div class="article-header-text">
                <a href="/users/1/자바지기" class="article-author-name">${answer.writer.name}</a>
                <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                    ${answer.time}
                </a>
            </div>
        </div>
        <div class="article-doc comment-doc">
            <p>${answer.contents}</p>
        </div>
         <div class="article-util answer_div">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form" action="/questions/${answer.question.id}/answers/${answer.id}" method="POST">
                        <input type="hidden" name="_method" value="DELETE">
                        <button type="button" class="delete-answer-button" onclick="deleteAnswerHandler(this);" value="${answer.id}/${answer.question.id}">삭제</button>
                    </form>
                </li>
            </ul>
        </div>
    </article>`
    $("#answer-container").insertAdjacentHTML("beforeend", html);
    var countElement = document.getElementById('comment-count');
    countElement.firstElementChild.innerHTML = Number(countElement.firstElementChild.innerHTML) + 1;
}

function deleteAnswer({message, answer}) {
    if (message !== "ok") {
        alert(message);
        return;
    }
    var article = document.getElementById('answer-' + answer.id);
    var countElement = document.getElementById('comment-count');
    countElement.firstElementChild.innerHTML = Number(countElement.firstElementChild.innerHTML) - 1;
    article.parentNode.removeChild(article);
}

function createAnswerHandler(evt) {
    evt.preventDefault();
    const content = $("#answer-content").value;
    $("#answer-content").value = "";

    fetchManager({
        url: '/api/questions/' + evt.target.value + '/answers',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({"contents": content}),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(target) {
    if(target.className !== "delete-answer-button") return;

    var value = target.value.split("/");
    const url = "/api/questions/" + value[1] + "/answers/" + value[0] + "";
//    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        headers: { 'content-type': 'application/json'},
        callback: deleteAnswer
    })
}

function initEvents() {
    const answerCreateBtn = $(".answer-create-button");
    answerCreateBtn.addEventListener("click", function (e) {
        if( e.target && e.target.nodeName == 'BUTTON' ) {
            e.preventDefault();
            createAnswerHandler(e);
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})
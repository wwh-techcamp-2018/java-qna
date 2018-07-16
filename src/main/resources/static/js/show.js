


document.addEventListener("DOMContentLoaded", function(evt) {
    console.log("Beginning script");

    bindDeleteHandlers();

    const submitWrite = select(".submit-write");
    const submitButton = submitWrite.querySelector("button");
    submitButton.addEventListener("click", (evt) => {
        evt.preventDefault();
        const text = submitWrite.querySelector("textarea").value;
        const url = submitWrite.getAttribute("action");
        addAnswerHandler(url, text);

        submitWrite.querySelector("textarea").value = "";
    })

});

function addAnswer({contents, question, writer, date, id}) {
    const html = `
<article class="article" id="answer-${id}">
        <div class="article-header">
        <div class="article-header-thumb">
        <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
        </div>
        <div class="article-header-text">

        <a href="/users/1/자바지기" class="article-author-name">${writer.name}</a>

        <a href="#answer-1434" class="article-header-time" title="퍼머링크">
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
        <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
        </li>
        <li>
        <form class="delete-answer-form" action="/questions/${question.id}/answers/${id}" method="POST">
        <input type="hidden" name="_method" value="DELETE">
        <button type="submit" class="delete-answer-button">삭제</button>
        </form>
        </li>
        </ul>
        </div>
        </article>`;

    select(".submit-write").insertAdjacentHTML("beforebegin", html);

}

function bindDeleteHandlers() {

    const answerHolder = select(".qna-comment");
    answerHolder.addEventListener("click", (evt) => {
        if (evt.target && evt.target.matches("button.delete-answer-button")) {
            evt.preventDefault();
            console.log("Anchor element clicked!");
            const answer = evt.target.closest("article.article");
            deleteAnswerHandler(evt.target.parentNode.getAttribute("action"), answer);
        }
    });
}

function addAnswerHandler(url, text) {
    const postObject = {"contents": text};
    console.log(postObject);
    fetchManager({
        url: url,
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify(postObject),
        callBack: addAnswer
    })
    
}

function deleteAnswer() {
    this.parentNode.removeChild(this);
}


function deleteAnswerHandler(url, answer) {

    fetchManager({
        url,
        method: 'delete',
        headers: { 'Content-type': 'application/json'},
        action: url,
        body: "",
        callBack : deleteAnswer.bind(answer)
    })
}

function selectAll(selector) {
    return document.querySelectorAll(selector);
}

function select(selector) {
    return document.querySelector(selector);
}

function fetchManager({ url, method, headers, action, body, callBack }) {
    fetch(url, {
        'action': action,
        'method': method,
        'headers': headers,
        'body': body,
        credentials: "same-origin"
    }).then((response) => {
        return response.json();
    }).then((result) => {
        callBack(result);
        console.log(result);
    });
}
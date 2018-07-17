function appendAnswer({id, contents, question, writer}) {
    const html = `
        <article class="article" id="answer-${id}">
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

    $(".qna-comment-slipp-articles")[0].insertAdjacentHTML("afterbegin", html);
}

function removeAnswer({id}) {

        $("#answer-"+id)[0].remove();
}

function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
    initEventsDelete();
})

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".submit-write textarea")[0].value;
    $(".submit-write textarea")[0].value = "";
    fetchManager({
        url:$(".submit-write")[0].action,
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function initEvents() {
    const answerBtn = $(".submit-write .btn");
    if(answerBtn === null) return;
    answerBtn[0].addEventListener("click", registerAnswerHandler);
}

function initEventsDelete() {
    const deleteBtns = document.getElementById('qna-comment-slipp-articles');
    if(deleteBtns == null) return;
    deleteBtns.addEventListener("click", removeAnswerHandler);
 }

function fetchManager({ url, method, body, headers, callback }) {
    fetch(url, {method,body,headers,credentials: "same-origin"})
        .then((response) => {
        return response.json()
    }).then((result) => {
        callback(result)
    }).catch((e)=>{
        alert("에러입니다.")
    console.log(e)})
}


function removeAnswerHandler(evt) {
    evt.preventDefault();
    const target = evt.target;
    if(target.className != "delete-answer-button") return ;
    var url = target.parentElement.action;
    console.log(url)
    fetchManager({
        url: url,
        method: 'DELETE',
        headers: { 'content-type': 'application/json'},
        callback: removeAnswer
    })
}

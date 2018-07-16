function appendAnswer(url, {contents, writer, date, id}) {
    const html = `
    <article class="article" id="answer-${id}">
          <div class="article-header">
              <div class="article-header-thumb">
                  <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
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
                      <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
                  </li>
                  <li>
                      <form class="delete-answer-form" action="${url}/${id}" method="POST">
                          <input type="hidden" name="_method" value="DELETE">
                          <button type="submit" class="delete-answer-button">삭제</button>
                      </form>
                  </li>
              </ul>
          </div>
      </article>`

    $(".qna-comment-slipp-articles").insertAdjacentHTML("beforeend", html);

    const countElement = $(".qna-comment-count > strong");
    let count = countElement.innerHTML;
    countElement.innerHTML = 1 + Number.parseInt(count);
}

function deleteAnswer(url, {answerId}) {
    const selector = `.article[id='answer-${answerId}']`;
    const target = $(selector);
    target.remove();
}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $(".form-answer").value;
    $(".form-answer").value = "";
    const url = $(".submit-write").action;

    //TODO 이거 1 고쳐야됨
    fetchManager({
        url: url,
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    if(evt.target.className !== "delete-answer-button") return;
    console.log(evt.target);
    evt.preventDefault();
    const url = evt.target.parentElement.action;
    const id = url.replace(/.+\/(\d+)$/, "$1");

    fetchManager({
        url,
        method: 'DELETE',
        headers: { 'content-type': 'application/json'},
        body: {id},
        callback: deleteAnswer 
    })
}

function initEvents() {
    const answerBtn = $(".answer-form");
    const answer = $(".qna-comment-slipp-articles");
    answerBtn.addEventListener("click", registerAnswerHandler);
    answer.addEventListener("click", deleteAnswerHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})
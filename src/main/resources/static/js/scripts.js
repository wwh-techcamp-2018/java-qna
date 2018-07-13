function $(selector) {
  return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
  initEvents();
})


function initEvents() {
    const answerBtn = $(".submit-write .btn");
    const answer = $(".qna-comment-slipp-articles");
    answerBtn && answerBtn.addEventListener("click", registerAnswerHandler);
    answer && answer.addEventListener("click", deleteAnswerHandler);
}

function deleteAnswer({id}) {
    const target = $(`#answer-${id}`);
    target.parentNode.removeChild(target);
}

function deleteAnswerHandler(evt) {
    if(evt.target.className !== "delete-answer-button") return;
    evt.preventDefault();

    const qid = $("input[name=questionId]").value;
    const id = $("input[name=answerId]").value;
    const url = `/api/questions/${qid}/answers/${id}`;
    fetchManager({
        url,
        method: 'DELETE',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({id}),
        callback: deleteAnswer
    })
}

function registerAnswerHandler(evt) {
  evt.preventDefault();
  const textarea = $(".submit-write textarea");
  const comment = textarea.value;
  textarea.value = "";

  const questionId = $(".submit-write input[name=questionId]");

  fetchManager({
      url: `/api/questions/${questionId.value}/answers`,
      method: 'POST',
      headers: { 'content-type': 'application/json'},
      body: JSON.stringify({comment}),
      callback: appendAnswer
  })
}

function fetchManager({ url, method, body, headers, callback }) {
  fetch(url, {method,body,headers,credentials: "same-origin"})
  .then((response) => {
      return response.json()
  })
  .then((result) => {
      callback(result)
  })
}

function appendAnswer({id, comment, question, writer}) {
    const html = `
        <article class="article" id="answer-${id}">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <a href="/users/${writer.id}" class="article-author-name">${writer.name}</a>
                    <div class="article-header-time">2018-07-13 h:m:s</div>
                </div>
            </div>
            <div class="article-doc comment-doc">
                ${comment}
            </div>
            <div class="article-util">
            <ul class="article-util-list">
                <li>
                    <a class="link-modify-article" href="/api/questions/${question.id}/answers/${id}">수정</a>
                </li>
                <li>
                    <form class="delete-answer-form">
                        <input type="hidden" name="answerId" value="${id}">
                        <button type="submit" class="delete-answer-button">삭제</button>
                    </form>
                </li>
            </ul>
            </div>
        </article> `
    $(".qna-comment-slipp-articles").insertAdjacentHTML("afterbegin", html);
}


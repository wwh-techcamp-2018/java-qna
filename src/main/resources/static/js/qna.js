function appendAnswer({result, data}) {
    if(result.type) {
        const contents = data.contents;
        const writer = data.writer.name;
        const writeTime = data.writeTime;
        const id = data.id;
        const questionId = data.question.id;
        const html = `
    <article class="article" id="answer-${id}">
                                <div class="article-header">
                                    <div class="article-header-thumb">
                                        <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                                             class="article-author-thumb" alt="">
                                    </div>
                                    <div class="article-header-text">
                                        <a href="/users/1/자바지기" class="article-author-writer">${writer}</a>
                                        <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                            ${writeTime}
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
                                               href="/questions/${questionId}/answers/${id}/form">수정</a>
                                        </li>
                                        <li>
                                                <input type="hidden" name="_method" value="DELETE">
                                                <button type="button">
                                                <a class="answer-delete" href="/api/questions/${questionId}/answers/${id}" >삭제</a>
                                                </button>
                                        </li>
                                    </ul>
                                </div>
                            </article> `;

        $(".answers").insertAdjacentHTML("beforeend", html);
        return ;
    }
    alert(result.msg);
    return;
}

function deleteAnswer({result, data}) {
    if(result.type){
        const selector = `#answer-${data.id}`;
        const target = $(selector);
        target.parentNode.removeChild(target);
        return;
    }
    alert(result.msg);
    return;

}

function registerAnswerHandler(evt) {
    evt.preventDefault();
    const contents = $("#answerAddBtn").value;
    $("#answerAddBtn").value = "";
    const questionId =  $("#questionId").value;

    fetchManager({
        url: '/api/questions/'+questionId+'/answers',
        method: 'POST',
        headers: { 'content-type': 'application/json'},
        body: JSON.stringify({contents}),
        callback: appendAnswer
    })
}

function deleteAnswerHandler(evt) {
    if(evt.target.className !== "answer-delete") return;
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
    const answerBtn = $(".answer-form .btn");
    const answer = $(".answers");
    answerBtn.addEventListener("click", registerAnswerHandler);
    answer.addEventListener("click", deleteAnswerHandler);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

package codesquad.dto;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;

import java.util.List;

public class QnAObject {

    private User writer;
    private boolean isWriter;
    private Question question;
    private List<Answer> answers;
    private int answersLength;

    public QnAObject(User questionUser, boolean isWriter, Question question, List<Answer> answers, int answersLength) {
        this.writer = questionUser;
        this.isWriter = isWriter;
        this.question = question;
        this.answers = answers;
        this.answersLength = answersLength;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean isWriter() {
        return isWriter;
    }

    public void setWriter(boolean writer) {
        isWriter = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getAnswersLength() {
        return answersLength;
    }

    public void setAnswersLength(int answersLength) {
        this.answersLength = answersLength;
    }
}

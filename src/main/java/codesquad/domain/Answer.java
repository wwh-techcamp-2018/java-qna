package codesquad.domain;

import codesquad.exception.UserNotMatchException;

import javax.persistence.*;

@Entity
public class Answer extends Post {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    public Answer() {
    }

    public Answer(User writer, Question question, String contents){
        super(writer, contents);
        this.question = question;
    }

    public Answer(boolean deleted) {
        super(deleted);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


    @Override
    public void validWriter(User user) {
        if (isWriter(user)) {
            throw new UserNotMatchException("/questions/" + question.getId());
        }
    }
}

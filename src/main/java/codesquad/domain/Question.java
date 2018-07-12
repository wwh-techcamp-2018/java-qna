package codesquad.domain;

import codesquad.exception.NullAnswerException;
import codesquad.exception.UserNotMatchException;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question extends Post {
    @Size(min = 1)
    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }


    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        if (answer == null)
            throw new NullAnswerException();
        answer.setQuestion(this);
        this.answers.add(answer);
    }

    public Answer findAnswerById(Long answerId) {
        return answers.stream().filter(answer -> answer.isId(answerId)).findFirst().orElseThrow(NullAnswerException::new);
    }

    public void removeAnswer(Long answerId, User user) {
        Answer answer = findAnswerById(answerId);
        answer.delete(user);
    }

    public void deleteQuestionAndAnswers(User sessionedUser) {
        delete(sessionedUser);
        for (Answer answer : answers) {
            answer.delete(sessionedUser);
        }
    }

    public List<Answer> listNotDeleted() {
        return answers.stream().filter(answer -> !answer.isDeleted()).collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void validWriter(User user) {
        if (isWriter(user)) {
            throw new UserNotMatchException("/questions/" + id);
        }
    }
}

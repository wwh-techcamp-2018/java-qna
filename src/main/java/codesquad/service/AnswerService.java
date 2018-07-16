package codesquad.service;

import codesquad.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AnswerService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public Answer create(User loginUser, Long questionId, Answer newAnswer) {
        Question savedQuestion = questionRepository.findById(questionId).get();
        return savedQuestion.createAnswer(loginUser, newAnswer);
    }

    @Transactional
    public void delete(User loginUser, Long questionId, Long id) {
        Question savedQuestion = questionRepository.findById(questionId).get();
        Answer savedAnswer = answerRepository.findById(id).get();
        savedQuestion.deleteAnswer(loginUser, savedAnswer);
    }
}

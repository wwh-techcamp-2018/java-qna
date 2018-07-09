package codesquad.service;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public void save(Question question) {
        if (question.getTitle().isEmpty())
            return;
        questionRepository.save(question);
    }

    public Question findById(Long id) {
        return questionRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    public void updateById(Long id, Question updateQuestion) {
        Question question = findById(id);
        question.updateQuestion(updateQuestion);
        questionRepository.save(question);
    }
}

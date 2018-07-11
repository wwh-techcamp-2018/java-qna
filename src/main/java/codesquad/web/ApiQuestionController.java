package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qnas")
public class ApiQuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public Iterable<Question> showList() {
        return questionRepository.findAll();
    }

    @PostMapping("{}/answers")
    public Answer addAnswer() {

        return null;
    }
}

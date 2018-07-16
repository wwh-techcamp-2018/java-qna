package codesquad.web;


import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.domain.exception.CustomAPIException;
import codesquad.domain.exception.CustomErrorMessage;
import codesquad.domain.exception.CustomException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @PostMapping("/api/questions/{questionId}/answers")
    public @ResponseBody Map<String, Object> createAnswer(@PathVariable long questionId, @RequestBody Map<String, String> body, HttpSession session){
        Map<String, Object> responseBody = new HashMap<>();
        try{
            Question question = getQuestionById(questionId);
            Answer answer = new Answer(SessionUtil.getSessionedUser(session), body.get("contents"));
            System.out.println(answer);
            System.out.println(question);

            question.addAnswer(answer);
            questionRepository.save(question);
            responseBody.put("result", Result.ok());
            responseBody.put("data", answerRepository.save(answer));
            return responseBody;
        } catch (CustomException exception) {
            throw new CustomAPIException(exception.getMessage());
        }

//        Question question = getQuestionById(questionId);
//        Answer answer = new Answer(SessionUtil.getSessionedUser(session), body.get("contents"));
//        System.out.println(answer);
//        System.out.println(question);
//
//        question.addAnswer(answer);
//        questionRepository.save(question);
//        return answerRepository.save(answer);
    }

    @Transactional
    @DeleteMapping("/api/questions/{questionId}/answers/{id}")
    public @ResponseBody Map<String, Object> deleteAnswer(@PathVariable long questionId, @PathVariable long id, HttpSession session){
        Map<String, Object> responseBody = new HashMap<>();
        try{
            Answer forDeleteAnswer = getAnswerById(id);
            Question forUpdateQuestion = getQuestionById(questionId);
            User sessionedUser = SessionUtil.getSessionedUser(session);
            forDeleteAnswer.delete(sessionedUser, forUpdateQuestion);
            questionRepository.save(forUpdateQuestion);
            answerRepository.save(forDeleteAnswer);
            responseBody.put("result", Result.ok());
            responseBody.put("data", forDeleteAnswer);
            return responseBody;
        } catch (CustomException exception) {
            throw new CustomAPIException(exception.getMessage());
        }
    }

    public Question getQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorMessage.NOT_VALID_PATH));
    }

    public Answer getAnswerById(long answerId){
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(CustomErrorMessage.INCORRECT_ACCESS));
    }
}

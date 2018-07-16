package codesquad.web;

import codesquad.domain.*;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.repository.UserRepository;
import codesquad.domain.exception.CustomErrorMessage;
import codesquad.domain.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
@Transactional
public class QnaController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/form")
    public String registForm(HttpSession session) {
        SessionUtil.validateLogin(session);
        return "/qna/form";
    }

    @PostMapping
    public String create(String title, String contents, HttpSession session) {

        User writeUser = SessionUtil.getSessionedUser(session);
        addQuestion(title, contents, writeUser);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("question", getQuestionById(id));
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String viewForUpdate(@PathVariable long id, Model model, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        model.addAttribute("question", getModifiableQuestion(sessionedUser, id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable long id, String contents, String title, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        updateQuestion(id, Question.newQuestion(sessionedUser, title, contents));
        return "redirect:/questions/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable long id, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        deleteQuestionById(sessionedUser, id);
        return "redirect:/";
    }

    @Transactional
    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        Question question = getQuestionById(questionId);
        Answer answer = new Answer(SessionUtil.getSessionedUser(session), contents);
        question.addAnswer(answer);
        questionRepository.save(question);
        return "redirect:/questions/{questionId}";
    }

    @Transactional
    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        User sessionedUser = SessionUtil.getSessionedUser(session);
        deleteAnswer(questionId, answerId, sessionedUser);
        return "redirect:/questions/{questionId}";
    }

    public void addQuestion(String title, String contents, User writer) {
        Question newQuestion = Question.newQuestion(writer, title, contents);
        questionRepository.save(newQuestion);
    }

    public void updateQuestion(long id, Question question) {
        Question targetQuestion = getQuestionById(id);
        targetQuestion.updateData(question);
        questionRepository.save(targetQuestion);
    }


    public Question getQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorMessage.NOT_VALID_PATH));
    }

    private Question getModifiableQuestion(User sessionedUser, long questionId) { //1. Question /
        Question targetQuestion = getQuestionById(questionId);
        targetQuestion.checkDeleteAuthority( sessionedUser);
        return targetQuestion;
    }

    @Transactional
    public void deleteQuestionById(User user, long questionId) {
        Question targetQuestion = getQuestionById(questionId);
        targetQuestion.deleteQuestion(user);
        questionRepository.save(targetQuestion);
        answerRepository.saveAll(targetQuestion.getAnswers());
    }

    @Transactional
    public void deleteAnswer(long questionId, long answerId, User sessionedUser) {
        Answer forDeleteAnswer = getAnswerById(answerId);
        Question forUpdateQuestion = getQuestionById(questionId);
        forDeleteAnswer.delete(sessionedUser, forUpdateQuestion);
        questionRepository.save(forUpdateQuestion);
        answerRepository.save(forDeleteAnswer);
    }

    public Answer getAnswerById(long answerId){
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new CustomException(CustomErrorMessage.INCORRECT_ACCESS));
    }
}
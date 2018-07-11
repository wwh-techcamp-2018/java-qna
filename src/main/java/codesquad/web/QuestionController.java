package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.exception.UnAuthorizedDeleteException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.exception.InvalidLoginException;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qnas")
public class QuestionController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("qnas", questionRepository.findAll());
        return "index";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        question.setWriter(SessionUtil.getUser(session)
                .orElseThrow(() -> new InvalidLoginException("로그인이 필요합니다")));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).get();
        model.addAttribute("qna", question);
        model.addAttribute("answers", question.getAnswers());
        model.addAttribute("answerCount", question.getAnswers().size());
        return "/qna/show";
    }

    @GetMapping("/form")
    public String showForm(Model model, HttpSession session) {
        model.addAttribute("user", SessionUtil.getUser(session)
                .orElseThrow(() -> new InvalidLoginException("로그인이 필요합니다")));
        return "/qna/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Question question, Model model, HttpSession session) {
        if (!question.matchWriter(SessionUtil.getUser(session)
                .orElseThrow(() -> new InvalidLoginException("로그인이 필요합니다")).getUserId())) {
            throw new UnAuthorizedDeleteException("다른 사용자의 글을 수정할 수는 없습니다.");
        }
        model.addAttribute("qna", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question) {
        Question updatedQuestion = questionRepository.findById(id).get().updateQuestion(question);
        questionRepository.save(updatedQuestion);
        return "redirect:/qnas/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        SessionUtil.getUser(session)
                .orElseThrow(() -> new InvalidLoginException("로그인이 필요합니다")).getUserId();

        Question question = questionRepository.findById(id).get();
        if (!question.matchWriter(SessionUtil.getUserId(session))) {
            throw new UnAuthorizedDeleteException("다른 사용자의 글을 삭제할 수는 없습니다.");
        }
        questionRepository.save(question.delete(SessionUtil.getUser(session).get()));
        return "redirect:/qnas";
    }

    @PostMapping("/{questionId}/answers")
    public String create(@PathVariable Long questionId, Answer answer, HttpSession session) {
        answer.setQuestion(questionRepository.findById(questionId).get());
        answer.setWriter(SessionUtil.getUser(session).get());
        answerRepository.save(answer);
        return "redirect:/qnas/" + questionId;
    }

    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        Answer answer = answerRepository.findById(answerId).get();
        if(!answer.matchWriter(SessionUtil.getUser(session).get())) {
            throw new UnAuthorizedDeleteException("다른 사람의 댓글을 삭제할 수 없습니다.");
        }
        answerRepository.save(answer.delete());
        return "redirect:/qnas/" + questionId;
    }
}

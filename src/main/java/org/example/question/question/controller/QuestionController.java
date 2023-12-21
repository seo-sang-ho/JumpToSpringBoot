package org.example.question.question.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.answer.answer.controller.AnswerController;
import org.example.answer.answer.controller.AnswerController.AnswerForm;
import org.example.answer.answer.service.AnswerService;
import org.example.question.question.entity.Question;
import org.example.question.question.service.QuestionService;
import org.example.user.user.entity.SiteUser;
import org.example.user.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

//    @GetMapping("/list")
//    public String list(Model model){
//        List<Question> questionList = questionService.getList();
//        model.addAttribute("questionList",questionList);
//        return "question_list";
//    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        Question question = questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }

    @Getter
    @Setter
    public static class QuestionForm{
        @NotEmpty(message = "제목은 필수입니다.")
        @Size(max = 200)
        private String subject;
        @NotEmpty(message = "내용은 필수입니다.")
        private String content;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());
        questionService.create(questionForm.subject, questionForm.content,siteUser);
        return "redirect:/question/list";
    }
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0")int page){
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }
}

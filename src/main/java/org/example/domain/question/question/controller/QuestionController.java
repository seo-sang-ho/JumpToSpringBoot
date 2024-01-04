package org.example.domain.question.question.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.domain.answer.answer.controller.AnswerController;
import org.example.domain.question.question.entity.Question;
import org.example.domain.question.question.service.QuestionService;
import org.example.domain.user.user.entity.SiteUser;
import org.example.domain.user.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;

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
public String detail(Model model, @PathVariable("id") Integer id, AnswerController.AnswerForm answerForm){
    Question question = questionService.getQuestion(id);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    boolean hasPaidAuthority = authorities.stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_PAID"));

    if(question.isPaid() && !hasPaidAuthority){ // 유료글이며, 유료 회원이 아닐 때
        question.setContent("멤버쉽 회원에게만 보이는 내용입니다.");
    }

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
        private boolean paid;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());
        questionService.create(questionForm.subject, questionForm.content,siteUser, questionForm.paid);
        return "redirect:/question/list";
    }
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0")int page){
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging",paging);
        return "question_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id){
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s",id);
    }
}

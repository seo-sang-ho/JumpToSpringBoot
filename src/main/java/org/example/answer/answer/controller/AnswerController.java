package org.example.answer.answer.controller;

import lombok.RequiredArgsConstructor;
import org.example.answer.answer.service.AnswerService;
import org.example.question.question.entity.Question;
import org.example.question.question.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content")String content){
        Question question = questionService.getQuestion(id);
        answerService.create(question,content);
        return String.format("redirect:/question/detail/%s",id);
    }
}

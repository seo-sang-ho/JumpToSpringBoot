package org.example.question.question.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.question.question.entity.Question;
import org.example.question.question.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public String list(Model model){
        List<Question> questionList = questionRepository.findAll();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }
    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }
}

package org.example.question.question.service;

import lombok.RequiredArgsConstructor;
import org.example.question.question.entity.Question;
import org.example.question.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        return questionRepository.findAll();
    }
}

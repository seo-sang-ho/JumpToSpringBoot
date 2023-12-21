package org.example.answer.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.answer.answer.entity.Answer;
import org.example.answer.answer.repository.AnswerRepository;
import org.example.question.question.entity.Question;
import org.example.user.user.entity.SiteUser;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        answerRepository.save(answer);
    }
}

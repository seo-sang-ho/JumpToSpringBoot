package org.example.question.question.repository;

import org.example.domain.question.question.entity.Question;
import org.example.domain.question.question.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void t1(){
        Question question = new Question();
        question.setSubject("sbb가 무엇인가요?");
        question.setContent("sbb에 대해 알고 싶습니다.");
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    @Test
    void t2(){
        Question question = new Question();
        question.setSubject("스프링부트 모델이 무엇인가요?");
        question.setContent("id는 자동으로 저장되나요?");
        question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
    }

    @Test
    void t3(){
        List<Question> all = questionRepository.findAll();
        assertEquals(2,all.size());

        Question question = all.get(0);
        assertEquals("sbb가 무엇인가요?",question.getSubject());
    }
    @Test
    void t4(){
        Optional<Question> oq = questionRepository.findById(1);
        if(oq.isPresent()){
            Question question = oq.get();
            assertEquals("sbb가 무엇인가요?",question.getSubject());
        }
    }
    @Test
    void t5(){
        Question oq = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1,oq.getId());
    }
    @Test
    void t6(){
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
        assertEquals(1,q.getId());
    }
    @Test
    void t7(){
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?",q.getSubject());
    }

    @Test
    void t8(){
        for(int i = 1; i < 300; i++){
            Question question = new Question();
            String subject = String.format("테스트 데이터입니다:[%03d]",i);
            String content = "내용무";
            question.setSubject(subject);
            question.setContent(content);
            question.setCreateDate(LocalDateTime.now());
            question.setAuthor(null);
            questionRepository.save(question);
        }
    }
}

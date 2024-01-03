package org.example.global.initData;

import lombok.RequiredArgsConstructor;
import org.example.domain.question.question.service.QuestionService;
import org.example.domain.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;

    private final UserService userService;
    private final QuestionService questionService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initNotProd(){
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1(){
        createUser();
        createQuestion();
    }

    public void createUser(){
        if(userService.count() >= 100) return;
        for(int i = 1; i <= 100; i++){
            userService.create("user"+i, "user"+i+"@email.com", passwordEncoder.encode("password"+i), true);
        }
    }

    public void createQuestion(){
        if(questionService.count() >= 100) return;
        for(int i = 1; i <= 100; i++){
            questionService.create("제목"+ i, "내용" + i, userService.getUser("user"+i), true);
        }
    }

}

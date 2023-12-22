package org.example.answer.answer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.question.question.entity.Question;
import org.example.user.user.entity.SiteUser;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;
}

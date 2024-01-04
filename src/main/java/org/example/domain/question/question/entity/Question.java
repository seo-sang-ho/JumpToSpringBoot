package org.example.domain.question.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.domain.answer.answer.entity.Answer;
import org.example.domain.user.user.entity.SiteUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;

    private boolean paid; // 유료 글 여부
}

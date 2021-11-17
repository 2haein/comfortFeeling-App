package com.codeboogie.comfortbackend.cheerup.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@Document(collection = "cheerup")
@ToString
public class CheerUp {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private String id;
    private int score; //긍정1 부정0
    private String context; //내용

    @Builder //빌더로 entity 만들어 한번에 저장
    public CheerUp(String id, int score, String context) {
        this.id = id;
        this.score = score;
        this.context = context;
    }

}

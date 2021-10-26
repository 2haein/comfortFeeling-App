package com.codeboogie.comfortbackend.feeling.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Document(collection = "comment")
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private String id;
    private String feeling_id; //원글 ID
    private Long userId; //댓글 작성자 ID
    private String context; //내용
    private Date publishDate; //등록일
    private int show; // 0 : 글 표시 1 : 글 삭제 상태

    @Builder //빌더로 entity 만들어 한번에 저장
    public Comment(String id, String feeling_id, Long userId, String context, Date publishDate, int show) {
        this.id = id;
        this.feeling_id = feeling_id;
        this.userId = userId;
        this.context = context;
        this.publishDate = publishDate;
        this.show = show;
    }

}
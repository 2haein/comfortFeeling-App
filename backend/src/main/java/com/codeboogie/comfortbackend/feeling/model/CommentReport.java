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

@Entity
@Getter
@NoArgsConstructor
@Document(collection = "commentReport")
@ToString
public class CommentReport {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private String id;
    private String feeling_id; //원글 ID
    private String comment_id; //댓글 ID
    private Long userId; //댓글 작성자 ID
    private int report; //신고 수

    @Builder //빌더로 entity 만들어 한번에 저장
    public CommentReport(String id, String feeling_id, String comment_id, Long userId, int report) {
        this.id = id;
        this.feeling_id = feeling_id;
        this.comment_id = comment_id;
        this.userId = userId;
        this.report = report;
    }
}

package com.codeboogie.comfortbackend.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 한승남
 * @version 1.0, 2021.09.28 소스 수정
 * 감정 기록 API 객체 모델 정의
 *
 */

@Getter
@NoArgsConstructor
@Document(collection = "feeling")
@ToString
public class Feeling {
    @Id
    private String id; //id
    private String userId;
    private int score; //감정 기록 점수
    private Date publishDate; //작성한 날짜
    private String text; //작성한 글
    private float xcoord; //글 작성 위치 x 좌표
    private float ycoord; //글 작성 위치 y 좌표

    @Builder //빌더로 entity 만들어 한번에 저장
    public Feeling(String id, String userId, int score, Date publishDate, String text, float xcoord, float ycoord) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.publishDate = publishDate;
        this.text = text;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }
}

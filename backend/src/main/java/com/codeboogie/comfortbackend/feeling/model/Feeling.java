package com.codeboogie.comfortbackend.feeling.model;

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

@Entity
@Getter
@NoArgsConstructor
@Document(collection = "feeling")
@ToString
public class Feeling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long userId; //카카오 계정
    private int score; //감정 기록 점수
    private Date publishDate; //작성한 날짜
    private Date updateDate; //수정한 날짜
    private String text; //작성한 글
    private Double xcoord; //글 작성 위치 x 좌표
    private Double ycoord; //글 작성 위치 y 좌표
    private Double anon_xcoord; //타 사용자가 보기 위한 임의의 x 좌표
    private Double anon_ycoord; //타 사용자가 보기 위한 임의의 y 좌표

    @Builder //빌더로 entity 만들어 한번에 저장
    public Feeling(String id, Long userId, int score, Date publishDate, Date updateDate, String text, Double xcoord, Double ycoord, Double anon_xcoord, Double anon_ycoord) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.publishDate = publishDate;
        this.updateDate = updateDate;
        this.text = text;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.anon_xcoord = anon_xcoord;
        this.anon_ycoord = anon_ycoord;
    }
}

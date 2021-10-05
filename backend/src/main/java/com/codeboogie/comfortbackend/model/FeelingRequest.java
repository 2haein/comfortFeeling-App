package com.codeboogie.comfortbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FeelingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; //카카오 계정
    private int score; //감정 기록 점수
    private Date publishDate; //작성한 날짜
    private String text; //작성한 글
    private float xcoord; //글 작성 위치 x 좌표
    private float ycoord; //글 작성 위치 y 좌표
}

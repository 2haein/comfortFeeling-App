package com.codeboogie.comfortbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

/**
 * @author 한승남
 * @version 1.0, 2021.09.28 소스 수정
 * 감정 기록 API 객체 모델 정의
 *
 */

@Data
@AllArgsConstructor
public class Feeling {
    private String id; //id
    private int score; //감정 기록 점수
    private Date publishDate; //작성한 날짜
    private String text; //작성한 글
    private float xcoord; //글 작성 위치 x 좌표
    private float ycoord; //글 작성 위치 y 좌표
}

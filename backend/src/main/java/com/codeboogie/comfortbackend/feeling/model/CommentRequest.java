package com.codeboogie.comfortbackend.feeling.model;

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
public class CommentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String feeling_id; //원글 ID
    private Long userId; //카카오 계정
    private String context; //작성한 글
    private Date publishDate; //작성한 날짜
    private int show; // 0 : 글 표시 1 : 글 삭제 상태
}
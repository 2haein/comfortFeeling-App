package com.codeboogie.comfortbackend.controller;

import com.codeboogie.comfortbackend.model.FeelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codeboogie.comfortbackend.model.Feeling;

import java.util.Date;

/**
 * @author 한승남
 * @version 1.0, 2021.09.28 소스 수정
 * 감정 기록 API RestController
 *
*/

@RequestMapping("/feeling")
@RestController
public class FeelingController {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

    @Autowired
    private FeelingRepository feelingRepository;

    private void insert() {
        Feeling entity = Feeling.builder()
                .userId("test")
                .score(3)
                .publishDate(new Date())
                .text("hi test")
                .xcoord(35)
                .ycoord(128)
                .build();

        //feelingRepository 또는 mongoTemplate 둘중 아무거나 선택 가능
        //feelingRepository.save(entity);

        mongoTemplate.insert(entity);

    }
}

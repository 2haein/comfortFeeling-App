package com.codeboogie.comfortbackend.feeling.controller;

import com.codeboogie.comfortbackend.feeling.model.FeelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import com.codeboogie.comfortbackend.feeling.model.Feeling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 한승남
 * @version 1.0, 2021.09.28 소스 수정
 * 감정 기록 API RestController
 *
*/


@RestController
@RequestMapping("api")
public class FeelingController {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

    @Autowired
    private FeelingService feelingService;

    @RequestMapping(path="/insert", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void insert(@RequestBody final Feeling feeling) {
        try {
            feelingService.insert(feeling);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path="/update", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void update(@RequestBody final Feeling feeling) {
        try {
            feelingService.update(feeling);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path="/remove", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void remove(@RequestBody final Feeling feeling) {
        String key = "id";
        String value = feeling.getId();
        try {
            feelingService.remove(key, value);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    //하루 글 썻는지 조회
    @RequestMapping(value="/history", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody long history(@RequestBody String userId, String date) {

        return feelingService.findDatas(userId, date);
    }

    // 전체 글 조회
    @RequestMapping(path="/history2", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> history2(@RequestBody String userId) {
        String value = userId;

        return feelingService.findHistory(value);
    }

    // 그래프 조회 년월일 전송 받을시 스코어 리턴
    @RequestMapping(path="/graph", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> graph(@RequestBody String userId, String startDate, String endDate) {

        return feelingService.getGraph(userId, startDate, endDate);
    }



}

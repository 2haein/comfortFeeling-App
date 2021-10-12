package com.codeboogie.comfortbackend.feeling.controller;

import com.codeboogie.comfortbackend.feeling.model.FeelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import com.codeboogie.comfortbackend.feeling.model.Feeling;

import java.util.ArrayList;
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

    //https://dion-ko.tistory.com/115

    //https://imasoftwareengineer.tistory.com/37 [삐멜 소프트웨어 엔지니어]

    @RequestMapping(value="/history", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody long history(@RequestBody String userId, String date) {

        return feelingService.findDatas(userId, date);
    }


    @RequestMapping(path="/insert", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void insert(@RequestBody final Feeling feeling) {
        List<String> errors = new ArrayList<>();
        try {
            feelingService.insert(feeling);
        } catch(final Exception e) {
            errors.add(e.getMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping(path="/update", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void update(@RequestBody final Feeling feeling) {
        List<String> errors = new ArrayList<>();
        try {
            feelingService.update(feeling);
        } catch(final Exception e) {
            errors.add(e.getMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping(path="/remove", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void remove(@RequestBody final Feeling feeling) {
        List<String> errors = new ArrayList<>();
        String key = "id";
        String value = feeling.getId();
        try {
            feelingService.remove(key, value);
        } catch(final Exception e) {
            errors.add(e.getMessage());
            e.printStackTrace();
        }
    }





}

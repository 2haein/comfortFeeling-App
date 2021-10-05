package com.codeboogie.comfortbackend.controller;

import com.codeboogie.comfortbackend.model.FeelingService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import com.codeboogie.comfortbackend.model.Feeling;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 한승남
 * @version 1.0, 2021.09.28 소스 수정
 * 감정 기록 API RestController
 *
*/


@RestController
public class FeelingController {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

    @Autowired
    private FeelingService feelingService;

    //https://dion-ko.tistory.com/115

    //https://imasoftwareengineer.tistory.com/37 [삐멜 소프트웨어 엔지니어]

/*    @RequestMapping(value="/", method={ RequestMethod.GET, RequestMethod.POST })
    public String home( HttpServletRequest request ) throws Exception {
        JSONObject json = new JSONObject();

        json.put("success", true);
        json.put("data", 10);
        json.put(null, 10);

        return json.toString();
    }*/

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Feeling insert(@RequestBody final Feeling feeling) {
        List<String> errors = new ArrayList<>();
        try {
            feelingService.insert(feeling);
        } catch(final Exception e) {
            errors.add(e.getMessage());
            e.printStackTrace();
        }
        return feeling;
    }

    // 삭제 기능
    /*@GetMapping("/remove")
    public void remove(String key, String value) {
        Criteria criteria = new Criteria(key);
        criteria.is(value);

        Query query = new Query(criteria);

        mongoTemplate.remove(query, "feeling");

    }*/

}

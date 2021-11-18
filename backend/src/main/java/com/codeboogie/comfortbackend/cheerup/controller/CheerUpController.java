package com.codeboogie.comfortbackend.cheerup.controller;

import com.codeboogie.comfortbackend.cheerup.model.CheerUp;
import com.codeboogie.comfortbackend.cheerup.model.CheerUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author 한승남
 * @version 1.0, 2021.11.17 소스 수정
 * 감정 위로글 API RestController
 *
 */

@RestController
@RequestMapping("api")
public class CheerUpController {

    @Autowired
    private CheerUpService cheerUpService;

    @RequestMapping(path="/message", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody String insert() {
        String msg = "";

        try {
            msg = cheerUpService.message();
        } catch(final Exception e) {
            e.printStackTrace();
        }
        System.out.println("서버 -> 안드로이드로 Message Push :"+ msg);

        return msg;
    }

}

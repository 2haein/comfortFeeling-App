package com.codeboogie.comfortbackend.controller;

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

    @GetMapping("/add")
    public Feeling addFeeling(){
        return new Feeling("abc", 3, new Date(), "기분", 35, 128);
    }
}

package com.codeboogie.comfortbackend.api.member.controller;

import com.codeboogie.comfortbackend.api.member.service.MemberService;
import com.codeboogie.comfortbackend.common.member.domain.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member", method= {RequestMethod.POST})
    public @ResponseBody void insert(@RequestParam HashMap<String, String> data, MemberDTO memberDTO) {
        System.out.println("안드로이드 -> 서버로 Post 요청"+ data+ ":"+ data.get("userId"));

        try{

            String userId = String.valueOf(data.get("userId"));
            String userName = String.valueOf(data.get("userName"));
            memberDTO.setUserId(userId);
            memberDTO.setUserName(userName);

            memberService.createMember(memberDTO);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

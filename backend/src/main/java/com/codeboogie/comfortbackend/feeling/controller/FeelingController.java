package com.codeboogie.comfortbackend.feeling.controller;

import com.codeboogie.comfortbackend.feeling.model.Comment;
import com.codeboogie.comfortbackend.feeling.model.CommentReport;
import com.codeboogie.comfortbackend.feeling.model.FeelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import com.codeboogie.comfortbackend.feeling.model.Feeling;

import java.text.ParseException;
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
    //삭제 (userId, publishDate) 전송
    @RequestMapping(path="/remove", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void remove(@RequestBody HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 삭제:"+ data);
        try {
            feelingService.remove(data);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    //하루 글 썻는지 조회
    @RequestMapping(value="/history", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody long history(@RequestBody HashMap<String, String> data) throws ParseException {
        System.out.println("안드로이드 -> 서버로 Post 요청 userId:"+ data.get("userId") + " date:" + data.get("publishDate"));

        return feelingService.findDatas(data.get("userId"), data.get("publishDate"));
    }

    //주변 글 개수 불러와서 지도에 포인터 표출
    @RequestMapping(path="/loadDataCount", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int loadDataCount(@RequestBody HashMap<String, String> data) throws Exception {
        System.out.println("안드로이드 -> 서버로 Post 요청 date:"+ data.get("publishDate"));

        return feelingService.loadDataCount(data.get("publishDate"));
    }

    // 주변 글 불러와서 지도에 포인터 표출
    @RequestMapping(path="/loadData", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> loadData(@RequestBody HashMap<String, String> data) throws Exception {
        System.out.println("안드로이드 -> 서버로 Post 요청 date:"+ data.get("publishDate"));

        return feelingService.loadData(data.get("publishDate"));
    }

    // 전체 글 조회
    @RequestMapping(path="/loadHistoryList", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Feeling> loadHistoryList(@RequestBody HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 요청 userId:"+ data);
        Long userId = Long.parseLong(data.get("userId"));


        return feelingService.loadHistoryList(userId);
    }

    // 글 조회
    @RequestMapping(path="/loadHistory", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Feeling loadHistory(@RequestBody HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 요청 글확인:"+ data);

        return feelingService.loadHistory(data);
    }

    // 글 팝업 조회
    @RequestMapping(path="/loadTodayHistory", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody Feeling loadTodayHistory(@RequestBody HashMap<String, String> data) throws ParseException {
        System.out.println("안드로이드 -> 서버로 Post 요청 글확인:"+ data);

        return feelingService.loadTodayHistory(data);
    }

    //댓글 추가
    @RequestMapping(path="/addCmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void addCmt(@RequestParam HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 댓글등록 :"+ data);
        try {
            feelingService.addCmt(data);
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }

    //댓글 조회
    @RequestMapping(path="/loadCmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<Comment> loadCmt(@RequestParam HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 댓글 요청 :"+ data);

        return feelingService.loadCmt(data);
    }

    //댓글 삭제
    @RequestMapping(path="/deleteCmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void deleteCmt(@RequestBody HashMap<String, String> data) {
        String comment_id = data.get("comment_id");
        System.out.println("안드로이드 -> 서버로 Post 댓글 삭제 :"+ comment_id);

        feelingService.deleteCmt(comment_id);
    }


    //댓글 신고 중복 방지
    @RequestMapping(path="/checkReportCmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int checkReportCmt(@RequestBody HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 댓글 신고 중복 방지 :"+ data);

        return feelingService.checkReportCmt(data);
    }

    //댓글 신고
    @RequestMapping(path="/reportCmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody void reportCmt(@RequestBody final CommentReport commentReport) {
        System.out.println("안드로이드 -> 서버로 Post 댓글 신고 :"+ commentReport);

        feelingService.reportCmt(commentReport);
    }

    //사용자 댓글 개수 확인 (댓글 제한을 위해)
    @RequestMapping(path="/limitCmt", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int limitCmt(@RequestBody HashMap<String, String> data) {
        System.out.println("안드로이드 -> 서버로 Post 댓글 개수 확인 :"+ data);

        return feelingService.limitCmt(data);
    }

    // 그래프 조회 년월일 전송 받을시 스코어 리턴
    // String 데이터 : userId, startDate & endDate (DateFormat : yyyy-MM-dd)
    @RequestMapping(path="/graph", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody int graph(@RequestParam HashMap<String, String> data) throws Exception {
        System.out.println("안드로이드 -> 서버로 Post 요청 :"+ data);

        return feelingService.getGraph(data.get("userId"), data.get("publishDate"));
    }

    // 그래프 월별 조회 : String 데이터 : userId / yyyy-MM (ex. 2021-10)
    @RequestMapping(path="/graphMonth", method={ RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody List<HashMap<String, Integer>> graphMonth(@RequestParam HashMap<String, String> data) throws Exception {
        System.out.println("안드로이드 -> 서버로 Post 요청 :"+ data);

        return feelingService.getGraphMonth(data.get("userId"), data.get("month"));
    }




}

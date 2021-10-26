package com.codeboogie.comfortbackend.feeling.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FeelingService {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

//    @Autowired
//    private FeelingRepository feelingRepository;

    public long findDatas(String userId, String date) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        Criteria criteria_arr[] = new Criteria[2];

        criteria_arr[0] = Criteria.where("userId").regex(userId);
        criteria_arr[1] = Criteria.where("publishDate").regex(date);

        query.addCriteria(criteria.andOperator(criteria_arr));

        return mongoTemplate.count(query, Feeling.class, "feeling");
    }

    public Feeling insert(final Feeling feeling) {
        if(feeling == null) {
            throw new NullPointerException("Data Null");
        }
        return mongoTemplate.insert(feeling);
    }

    public void update(final Feeling feeling) {
        if(feeling == null) {
            throw new NullPointerException("Data Null");
        }
        Criteria criteria = new Criteria("userId");
        criteria.is(feeling.getUserId());

        Query query = new Query();

        Update update = new Update();
        update.set("score", feeling.getScore());
        update.set("publishDate", feeling.getPublishDate());
        update.set("text", feeling.getText());
        update.set("xcoord", feeling.getXcoord());
        update.set("ycoord", feeling.getYcoord());

        mongoTemplate.updateFirst(query, update, "feeling");
    }

    public void remove(String key, String value) {
        Criteria criteria = new Criteria(key);
        criteria.is(value);

        Query query = new Query(criteria);

        mongoTemplate.remove(query, "feeling");

    }

    public List<Feeling> loadHistory(Long userId) {
        Criteria criteria = new Criteria("userId");
        criteria.is(userId);

        Query query = new Query(criteria);

        return mongoTemplate.find(query, Feeling.class, "feeling" );
    }

   /* public List<HashMap> load_cmt(String id) {
        Criteria criteria = new Criteria("_id");
        criteria.is(id);

        Query query = new Query(criteria);

        return mongoTemplate.find(query, , "feeling" );
    }*/

    public List<Feeling> getGraph(String userId, String publishDate) throws Exception {
        Query query = new Query();
        Criteria criteria = new Criteria();

        Criteria criteria_arr[] = new Criteria[2];
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String startDate = publishDate + "T00:00:00.000Z";
        Date sDate = inputFormat.parse(startDate);
        // 시간대가 UTC로 되기 때문에 9시간 추가해야 한국 시간대 맞춰짐
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.add(Calendar.HOUR, 9);
        sDate = cal.getTime();
        cal.add(Calendar.DATE, 1);
        Date eDate = cal.getTime();

        criteria_arr[0] = Criteria.where("userId").is(Long.parseLong(userId));
        criteria_arr[1] = Criteria.where("publishDate").gte(sDate).lte(eDate);

        query.addCriteria(criteria.andOperator(criteria_arr));
        query.fields().include("score", "publishDate");

        //System.out.println("query :"+ query);

        return mongoTemplate.find(query, Feeling.class, "feeling");
    }

    public List<Feeling> getGraphMonth(String userId, String month) throws Exception {
        Query query = new Query();
        Criteria criteria = new Criteria();

        Criteria criteria_arr[] = new Criteria[2];
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // 시작 시간 맞추기 입력값 예시 2021-10
        String startDate = month + "-01T00:00:00.000Z";
        Date sDate = inputFormat.parse(startDate);

        // 시간대가 UTC로 되기 때문에 9시간 추가해야 한국 시간대 맞춰짐
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.add(Calendar.HOUR, 9);
        sDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date eDate = cal.getTime();

        criteria_arr[0] = Criteria.where("userId").is(Long.parseLong(userId));
        criteria_arr[1] = Criteria.where("publishDate").gt(sDate).lt(eDate);

        query.addCriteria(criteria.andOperator(criteria_arr));
        query.fields().include("score", "publishDate");

        //System.out.println("query :"+ query);
        return mongoTemplate.find(query, Feeling.class, "feeling");
    }



}

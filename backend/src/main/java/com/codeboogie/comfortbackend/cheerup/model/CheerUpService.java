package com.codeboogie.comfortbackend.cheerup.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CheerUpService {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

    public String message() {
        String msg = "";
        //Criteria criteria = new Criteria("score");
        //criteria.equals(data.get("score"));
        //Query query = new Query(criteria);
        SampleOperation sampleOperation = Aggregation.sample(1L);
        //MatchOperation matchOperation = Aggregation.match(criteria);
        Aggregation aggregation = Aggregation.newAggregation(
                sampleOperation
                //matchOperation
        );
        msg = mongoTemplate.aggregate(aggregation, "cheerup", CheerUp.class).toString();

        return msg;
    }
}

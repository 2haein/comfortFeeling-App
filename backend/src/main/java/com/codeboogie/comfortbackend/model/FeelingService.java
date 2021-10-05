package com.codeboogie.comfortbackend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeelingService {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

//    @Autowired
//    private FeelingRepository feelingRepository;

    private List<Feeling> findDatas(String key, String value) {
        Criteria criteria = new Criteria(key);
        criteria.is(value);

        Query query = new Query(criteria);

        return mongoTemplate.find(query, Feeling.class, "feeling");
    }

    public Feeling insert(final Feeling feeling) {
        if(feeling == null) {
            throw new NullPointerException("Data null");
        }
        return mongoTemplate.insert(feeling);
    }


}

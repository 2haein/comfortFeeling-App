package com.codeboogie.comfortbackend.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeelingRepository extends MongoRepository<Feeling, String> {
}

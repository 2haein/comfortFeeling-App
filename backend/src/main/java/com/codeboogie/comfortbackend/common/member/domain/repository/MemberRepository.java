package com.codeboogie.comfortbackend.common.member.domain.repository;

import com.codeboogie.comfortbackend.common.member.domain.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member, String> {
     Member findByUserId(String userId);
}

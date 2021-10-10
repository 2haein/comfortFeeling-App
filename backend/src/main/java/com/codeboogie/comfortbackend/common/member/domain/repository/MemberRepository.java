package com.codeboogie.comfortbackend.common.member.domain.repository;

import com.codeboogie.comfortbackend.common.member.domain.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    Optional<Member> findById(String userId);
}

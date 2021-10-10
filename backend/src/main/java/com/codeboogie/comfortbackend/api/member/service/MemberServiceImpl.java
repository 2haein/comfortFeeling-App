package com.codeboogie.comfortbackend.api.member.service;

import com.codeboogie.comfortbackend.common.member.domain.dto.MemberDTO;
import com.codeboogie.comfortbackend.common.member.domain.model.Member;
import com.codeboogie.comfortbackend.common.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    @Autowired
    private final MemberRepository memberRepository;

    @Override
    public boolean createMember(MemberDTO memberDTO){
        // TODO: login id 검사 로직 추가
        try{
            Member member = new Member();
            member.setUserId(memberDTO.getUserId());
            member.setUserName(memberDTO.getUserName());

            Member memberResult = memberRepository.save(member);


        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}

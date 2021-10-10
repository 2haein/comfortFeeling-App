package com.codeboogie.comfortbackend.api.member.service;

import com.codeboogie.comfortbackend.common.member.domain.dto.MemberDTO;

public interface MemberService {
    boolean createMember(MemberDTO memberDTO);
}

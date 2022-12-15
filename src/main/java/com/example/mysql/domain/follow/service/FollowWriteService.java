package com.example.mysql.domain.follow.service;

import com.example.mysql.domain.member.dto.MemberDto;
import org.springframework.util.Assert;

import java.util.Objects;

public class FollowWriteService {

    public void create(MemberDto fromMember, MemberDto toMember) {
        /*
            from, to 회원 정보를 받아서 저장할텐데
            from <-> to validate
         */
        Assert.isTrue(fromMember.id(), toMember.id());
    }
}

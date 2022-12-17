package com.example.mysql.domain.follow.service;

import com.example.mysql.domain.follow.entity.Follow;
import com.example.mysql.domain.follow.repository.FollowRepository;
import com.example.mysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@RequiredArgsConstructor
@Service
public class FollowWriteService {

    private final FollowRepository followRepository;

    public void create(MemberDto fromMember, MemberDto toMember) {
        /*
            from, to 회원 정보를 받아서 저장할텐데
            from <-> to validate
         */
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "Form, To 회원이 동일합니다.");

        var follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();
        followRepository.save(follow);
    }
}

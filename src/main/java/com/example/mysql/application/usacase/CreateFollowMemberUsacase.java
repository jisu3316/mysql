package com.example.mysql.application.usacase;

import com.example.mysql.domain.follow.service.FollowWriteService;
import com.example.mysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * usacase 패키지는 여러 도메인의 흐름을 제어하는 역할을 한다.
 */
@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsacase {
    private final MemberReadService memberReadService;
    private final FollowWriteService followWriteService;
    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력 받은 memberId로 회원 조회
            2. FollowWriteService.create()
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);
        followWriteService.create(fromMember, toMember);
    }
}

package com.example.mysql.domain.member.service;

import com.example.mysql.domain.member.dto.RegisterMemberCommand;
import com.example.mysql.domain.member.entity.Member;
import com.example.mysql.domain.member.entity.MemberNicknameHistory;
import com.example.mysql.domain.member.repository.MemberNickNameHistoryRepository;
import com.example.mysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;
    private final MemberNickNameHistoryRepository memberNickNameHistoryRepository;

    public Member register(RegisterMemberCommand command) {
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
                - 닉네임은 10자를 넘길 수 없다.
            파라미터 - memberRegisterCommand

            val member = Member.of(memberRegisterCommand)
            memberRepository.save(member);
         */
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();
        var saveMember = memberRepository.save(member);
        saveMemberNicknameHistory(saveMember);
        return saveMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        var member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);
        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNickNameHistoryRepository.save(history);
    }
}

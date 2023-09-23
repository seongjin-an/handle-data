package com.ansj.domain.member.service;

import com.ansj.domain.member.dto.RegisterMemberCommand;
import com.ansj.domain.member.entity.Member;
import com.ansj.domain.member.entity.MemberNicknameHistory;
import com.ansj.domain.member.repository.MemberNicknameHistoryRepository;
import com.ansj.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    final private MemberRepository memberRepository;

    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;


    public Member register(RegisterMemberCommand command) {
        /* 수도코드
            목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
                - 닉네임은 10자를 넘길 수 없다.
            파라미터 - memberRegisterCommand
            Member member = Member.of(memgerRegisterCommand)
            memberRepository.save(member)
         */
        return getMember(command);
    }

    @Transactional
    private Member getMember(RegisterMemberCommand command) {
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthdate())
                .build();
        Member savedMember = memberRepository.save(member);
        double result = 0 / 0;
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
         var member = memberRepository.findById(memberId).orElseThrow();
         member.changeNickname(nickname);
         memberRepository.save(member);
         // TODO: 변경내역 히스토리를 저장한다.
        saveMemberNicknameHistory(member);

    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryRepository.save(history);
    }
}

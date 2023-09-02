package com.ansj.domain.member.service;

import com.ansj.domain.member.dto.RegisterMemberCommand;
import com.ansj.domain.member.entity.Member;
import com.ansj.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    final private MemberRepository memberRepository;

    public Member register(RegisterMemberCommand command) {
        /* 수도코드
            목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
                - 닉네임은 10자를 넘길 수 없다.
            파라미터 - memberRegisterCommand
            Member member = Member.of(memgerRegisterCommand)
            memberRepository.save(member)
         */
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthDay(command.birthdate())
                .build();
        return memberRepository.save(member);
    }
}

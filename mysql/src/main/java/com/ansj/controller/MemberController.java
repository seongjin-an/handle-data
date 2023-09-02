package com.ansj.controller;

import com.ansj.domain.member.dto.MemberDto;
import com.ansj.domain.member.dto.RegisterMemberCommand;
import com.ansj.domain.member.entity.Member;
import com.ansj.domain.member.service.MemberReadService;
import com.ansj.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    final private MemberWriteService memberWriteService;

    final private MemberReadService memberReadService;
    /*
        JPA 엔티티가 컨트롤러 계층에 존재하면, OSIV이슈가 생길 수 있음.
        프레젠테이션 레이어까지 와버리면, 프레젠테이션 레이어 요구사항으로 엔티티가 변경되는 상황이 발생되버림.
        DTO 에 포함시켜야 하는 것들이 추가된다면 엔티티도 영향을 받는 것이고, 불필요한 정보까지 응답하는 경우가 발생됨.
     */
    @PostMapping("/members")
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        Member member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberReadService.getMember(id);
    }

}

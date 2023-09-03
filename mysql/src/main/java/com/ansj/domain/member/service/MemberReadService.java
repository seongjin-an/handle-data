package com.ansj.domain.member.service;

import com.ansj.domain.member.dto.MemberDto;
import com.ansj.domain.member.dto.MemberNicknameHistoryDto;
import com.ansj.domain.member.entity.Member;
import com.ansj.domain.member.entity.MemberNicknameHistory;
import com.ansj.domain.member.repository.MemberNicknameHistoryRepository;
import com.ansj.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    final private MemberRepository memberRepository;
    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
        List<MemberNicknameHistory> histories = memberNicknameHistoryRepository.findAllByMemberId(memberId);
        return histories.stream().map(this::toDto).collect(Collectors.toList());
    }

    public MemberDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        var members = memberRepository.findAllByIdIn(ids);
        return members.stream().map(this::toDto).toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }
}

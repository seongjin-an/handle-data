package com.ansj.application.usecase;

import com.ansj.domain.follow.service.FollowWriteService;
import com.ansj.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUsecase {
    /*
        read, write service 를 분리한 이점은, 이 usecase는 회원에 대한 쓰기 권한이 아예 없음
     */
    final private MemberReadService memberReadService;
    final private FollowWriteService followWriteService;

    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력받은 memberId 로 회원 조회
            2. FollowWriteService.create()
         */
        var fromMember = memberReadService.getMember(fromMemberId);
        var toMember = memberReadService.getMember(toMemberId);
        followWriteService.create(fromMember, toMember);
        // 이렇게 구현하면 끝! usecase layer는 가능한 로직이 없어야 한다. 각 도메인에 대한 비즈니스 로직은 각 도메인 서비스에 있어야 하고,
        // usecase 는 각 도메인 서비스들의 흐름만 제어할 뿐이다.
    }
}

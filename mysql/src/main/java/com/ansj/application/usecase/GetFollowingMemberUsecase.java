package com.ansj.application.usecase;

import com.ansj.domain.follow.entity.Follow;
import com.ansj.domain.follow.service.FollowReadService;
import com.ansj.domain.member.dto.MemberDto;
import com.ansj.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMemberUsecase {

    final private MemberReadService memberReadService;
    final private FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId) {
        /*
            1. fromMemberId= memberID -> Follow list
            2. 1번을 순회하면서 회원정보를 찾으면 된다.
         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return memberReadService.getMembers(followingMemberIds);
    }

}

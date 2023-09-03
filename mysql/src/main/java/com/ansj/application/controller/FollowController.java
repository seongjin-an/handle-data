package com.ansj.application.controller;

import com.ansj.application.usecase.CreateFollowMemberUsecase;
import com.ansj.application.usecase.GetFollowingMemberUsecase;
import com.ansj.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    final private CreateFollowMemberUsecase createFollowMemberUsecase;

    final private GetFollowingMemberUsecase getFollowingMemberUsecase;

    @PostMapping("/{fromId}/{toId}")
    public void register(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUsecase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<MemberDto> getFollowings(@PathVariable Long fromId) {
        return getFollowingMemberUsecase.execute(fromId);
    }

}

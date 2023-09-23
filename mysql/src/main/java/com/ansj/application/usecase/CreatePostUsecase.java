package com.ansj.application.usecase;

import com.ansj.domain.follow.entity.Follow;
import com.ansj.domain.follow.service.FollowReadService;
import com.ansj.domain.post.dto.PostCommand;
import com.ansj.domain.post.service.PostWriteService;
import com.ansj.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostUsecase {

    final private PostWriteService postWriteService;

    final private FollowReadService followReadService;

    final private TimelineWriteService timelineWriteService;

    @Transactional
    public Long execute(PostCommand postCommand) {
        var postId = postWriteService.create(postCommand);

        var followMemberIds = followReadService.getFollowers(postCommand.memberId()).stream()
                .map(Follow::getFromMemberId)
                .toList();
        timelineWriteService.deliveryToTimeline(postId, followMemberIds);

        return postId;
    }

}

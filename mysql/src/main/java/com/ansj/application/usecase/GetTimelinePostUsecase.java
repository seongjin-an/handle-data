package com.ansj.application.usecase;


import com.ansj.domain.follow.entity.Follow;
import com.ansj.domain.follow.service.FollowReadService;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.service.PostReadService;
import com.ansj.util.CursorRequest;
import com.ansj.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {

    final private FollowReadService followReadService;

    final private PostReadService postReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /*
            1. memberId -> follow 조회
            2. 1번 결과로 게시물 조회
         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

}

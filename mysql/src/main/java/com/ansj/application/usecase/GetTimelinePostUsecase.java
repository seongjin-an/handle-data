package com.ansj.application.usecase;


import com.ansj.domain.follow.entity.Follow;
import com.ansj.domain.follow.service.FollowReadService;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.entity.Timeline;
import com.ansj.domain.post.service.PostReadService;
import com.ansj.domain.post.service.TimelineReadService;
import com.ansj.util.CursorRequest;
import com.ansj.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTimelinePostUsecase {

    final private FollowReadService followReadService;

    final private PostReadService postReadService;
    
    final private TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /*
            1. memberId -> follow 조회
            2. 1번 결과로 게시물 조회
         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeline(Long memberId, CursorRequest cursorRequest) {
        /*
            1. Timeline 테이블 조회
            2. 1번에 해당하는 게시물을 조회한다.
         */
        var pageTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        var postIds = pageTimelines.body().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPosts(postIds);
        return new PageCursor<>(pageTimelines.nextCursorRequest(), posts);
    }

}

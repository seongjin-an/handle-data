package com.ansj.application.controller;

import com.ansj.application.usecase.CreatePostUsecase;
import com.ansj.application.usecase.GetTimelinePostUsecase;
import com.ansj.domain.post.dto.DailyPostCount;
import com.ansj.domain.post.dto.DailyPostCountRequest;
import com.ansj.domain.post.dto.PostCommand;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.service.PostReadService;
import com.ansj.domain.post.service.PostWriteService;
import com.ansj.util.CursorRequest;
import com.ansj.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    final private PostWriteService postWriteService;
    final private PostReadService postReadService;
    final private GetTimelinePostUsecase getTimelinePostUsecase;
    final private CreatePostUsecase createPostUsecase;

    @PostMapping
    public Long create(PostCommand command) {
//        return postWriteService.create(command);
        return createPostUsecase.execute(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@ModelAttribute DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    /*
    {
      "page": 0,
      "size": 5,
      "sort": [
        "createdDate,desc"
      ]
    }
     */
    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
//            @RequestParam Integer page,
//            @RequestParam Integer size
            Pageable pageable
    ) {
//        return postReadService.getPosts(memberId, PageRequest.of(page, size));
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
//        return getTimelinePostUsecase.execute(memberId, cursorRequest);
        return getTimelinePostUsecase.executeByTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable Long postId) {
        postWriteService.likePost(postId);
    }

}

package com.ansj.application.controller;

import com.ansj.domain.post.dto.DailyPostCount;
import com.ansj.domain.post.dto.DailyPostCountRequest;
import com.ansj.domain.post.dto.PostCommand;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.service.PostReadService;
import com.ansj.domain.post.service.PostWriteService;
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

    @PostMapping
    public Long create(PostCommand command) {
        return postWriteService.create(command);
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

}

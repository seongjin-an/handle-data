package com.ansj.application.controller;

import com.ansj.domain.post.dto.DailyPostCount;
import com.ansj.domain.post.dto.DailyPostCountRequest;
import com.ansj.domain.post.dto.PostCommand;
import com.ansj.domain.post.service.PostReadService;
import com.ansj.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
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

}

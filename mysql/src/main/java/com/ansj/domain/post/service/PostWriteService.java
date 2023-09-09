package com.ansj.domain.post.service;

import com.ansj.domain.post.dto.PostCommand;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriteService {

    private final PostRepository postRepository;

    public Long create(PostCommand command) {
        var post = Post
                .builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();
        return postRepository.save(post).getId();
    }

}

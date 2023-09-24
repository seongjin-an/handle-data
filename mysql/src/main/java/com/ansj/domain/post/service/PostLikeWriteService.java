package com.ansj.domain.post.service;

import com.ansj.domain.member.dto.MemberDto;
import com.ansj.domain.post.dto.PostCommand;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.entity.PostLike;
import com.ansj.domain.post.repository.PostLikeRepository;
import com.ansj.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeWriteService {

    private final PostLikeRepository postLikeRepository;

    public Long create(Post post, MemberDto memberDto) {
        var postLike = PostLike
                .builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();
        return postLikeRepository.save(postLike).getPostId();
    }
}

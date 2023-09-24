package com.ansj.domain.post.service;

import com.ansj.domain.member.dto.PostDto;
import com.ansj.domain.post.dto.DailyPostCount;
import com.ansj.domain.post.dto.DailyPostCountRequest;
import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.repository.PostLikeRepository;
import com.ansj.domain.post.repository.PostRepository;
import com.ansj.util.CursorRequest;
import com.ansj.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalLong;

@RequiredArgsConstructor
@Service
public class PostReadService {

    final private PostRepository postRepository;

    final private PostLikeRepository postLikeRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
            반홥 값 -> 리스트 [일자, 작성회원, 작성 게시물 횟수]
            select createdDate, memberId, count(id)
            from Post
            where memberId = :memberId and createdDate between :firstDate and :lastDate
            group by createdDate, memberId
         */
        return postRepository.groupByCreatedDate(request);
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable).map(this::toDto);
    }

    private PostDto toDto(Post post) {
        return new PostDto(
                post.getId(), post.getContents(), post.getCreatedAt(), postLikeRepository.count(post.getId())
        );
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId, false).orElseThrow();
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        long nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);
        long nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByInId(ids);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdInAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdInAndOrderByIdDesc(memberIds, cursorRequest.size());
    }

    private long getNextKey(List<Post> posts) {
        var nextKey = posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
        return nextKey;
    }
}

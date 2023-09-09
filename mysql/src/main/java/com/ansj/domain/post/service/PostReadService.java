package com.ansj.domain.post.service;

import com.ansj.domain.post.dto.DailyPostCount;
import com.ansj.domain.post.dto.DailyPostCountRequest;
import com.ansj.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {

    final private PostRepository postRepository;

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

}

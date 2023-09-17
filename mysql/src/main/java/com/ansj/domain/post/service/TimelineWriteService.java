package com.ansj.domain.post.service;

import com.ansj.domain.post.entity.Timeline;
import com.ansj.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TimelineWriteService {

    final private TimelineRepository timelineRepository;

    public void deliveryToTimeline(Long postId, List<Long> toMemberids) {
        var timelines = toMemberids.stream()
                .map(toTimeline(postId)).toList();
        timelineRepository.bulkInsert(timelines);
    }

    private Function<Long, Timeline> toTimeline(Long postId) {
        return (memberId) -> Timeline.builder().memberId(memberId).postId(postId).build();
    }

}

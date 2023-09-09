package com.ansj.domain.post;

import com.ansj.domain.post.entity.Post;
import com.ansj.domain.post.repository.PostRepository;
import com.ansj.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 2, 1)
        );
//        IntStream.range(0, 5)
//                .mapToObj(i -> easyRandom.nextObject(Post.class))
//                .forEach(x ->
//                        postRepository.save(x)
//                );
        var stopWatch = new StopWatch();
        stopWatch.start();
        int thousand = 1000;
        var posts = IntStream.range(0, thousand * thousand)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();
        stopWatch.stop();
        System.out.println("객체 생성 시간 : " + stopWatch.getTotalTimeMillis());


        var queryStopWatch = new StopWatch();
        queryStopWatch.start();
        postRepository.bulkInsert(posts);
        queryStopWatch.stop();
        System.out.println("DB Insert 시간 : " + queryStopWatch.getTotalTimeMillis());
    }

}

package com.ansj.util;

import com.ansj.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.Random;

public class MemberFixtureFactory {
    static public Member create() {
        var param = new EasyRandomParameters();
        long l = new Random().nextLong();
        param.setSeed(l);
        return new EasyRandom(param).nextObject(Member.class);
    }
}

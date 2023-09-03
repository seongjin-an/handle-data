package com.ansj.domain.member;

import com.ansj.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void changeNicknameTest() throws Exception {
        var member = MemberFixtureFactory.create();
        var expected = "pnu";
        member.changeNickname(expected);
        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength() throws Exception {
        var member = MemberFixtureFactory.create();
        var exceeded = "pnu12345678";
        Assertions.assertThrows(IllegalArgumentException.class, () -> member.changeNickname(exceeded));
    }
}

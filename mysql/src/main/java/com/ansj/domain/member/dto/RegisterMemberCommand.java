package com.ansj.domain.member.dto;

import java.time.LocalDate;

// record 는 jdk 14에 도입되어 16에서 공식적 기능이 됨, record로 선언하면 getter, setter 등을 자동으로 만들어줌.
public record RegisterMemberCommand(
        String email,
        String nickname,
        LocalDate birthdate
) {
}

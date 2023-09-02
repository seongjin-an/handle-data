package com.ansj.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private final Long id;

    private String nickname;

    final private String email;

    final private LocalDate birthDay;

    final private LocalDateTime createdAt;

    final private static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthDay, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = Objects.requireNonNull(nickname);
        this.email = Objects.requireNonNull(email);

        validateNickname(nickname);
        this.birthDay = Objects.requireNonNull(birthDay);

        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}

package com.ansj.util;

public record CursorRequest(Long key, int size) {
    public static final Long NONE_KEY = -1L;

    /*
        키를 가지고 있는 정책이 바뀔 수 있다.
        이 바뀔 수도 있는 로직이 서비스 군대군대 들어가면 난잡해진다. 그러니 여기서 구현.
     */
    public boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}

package com.junhyxxn.back.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Provider {

    KAKAO, NAVER, GOOGLE, JUNHYXXN;

    @JsonCreator
    public static Provider from(String s) {
        return Provider.valueOf(s.trim().toUpperCase());
    }

}

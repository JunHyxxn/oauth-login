package com.junhyxxn.back.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {

    MAN, WOMAN;

    @JsonCreator
    public static Gender from(String s) {
        return Gender.valueOf(s.trim().toUpperCase());
    }

}

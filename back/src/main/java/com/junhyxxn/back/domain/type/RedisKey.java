package com.junhyxxn.back.domain.type;

public enum RedisKey {

    REFRESH_TOKEN("refresh_token");

    private final String keyName;

    RedisKey(String keyName) {
        this.keyName = keyName;
    }

    public String getKey(String id) {
        return keyName + ":" + id;
    }

}

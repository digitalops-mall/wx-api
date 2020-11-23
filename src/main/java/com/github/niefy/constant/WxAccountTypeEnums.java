package com.github.niefy.constant;

import java.util.Arrays;

public enum WxAccountTypeEnums {

    SUBSCRIPTION_ACCOUNT(1,"订阅号"),
    SERVICE_ACCOUNT(2,"服务号"),
    MINI_PROGRAM(3,"小程序");

    private int value;
    private String name;

    WxAccountTypeEnums(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getWxAccountTypeName(int value) {
        return WxAccountTypeEnums.getWxAccountType(value).getName();
    }

    public static WxAccountTypeEnums getWxAccountType(int value) {
        return Arrays.stream(WxAccountTypeEnums.values())
                .filter(v -> v.value == value).findFirst().orElseThrow(() -> new IllegalArgumentException("找不到对应的账户类型"));
    }
}

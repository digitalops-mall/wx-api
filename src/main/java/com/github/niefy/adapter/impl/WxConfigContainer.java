package com.github.niefy.adapter.impl;

import com.github.niefy.adapter.WxConfig;

import java.util.HashMap;
import java.util.Map;


public class WxConfigContainer {

    private static final WxConfigContainer wxConfigContainer = new WxConfigContainer();

    private final Map<String, WxConfig> wxConfigMap = new HashMap<>();

    private WxConfigContainer() {

    }

    Map<String, WxConfig> getWxConfigMap() {
        return wxConfigMap;
    }

    public static WxConfigContainer getInstance() {
        return wxConfigContainer;
    }

    public WxConfig getWxConfig(String appid) {
        return wxConfigMap.get(appid);
    }
}

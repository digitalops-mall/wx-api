package com.github.niefy.adapter.impl;

import com.github.niefy.adapter.WxConfig;
import com.github.niefy.constant.WxAccountTypeEnums;

public class WxMpDefaultConfigImpl extends me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl implements WxConfig {

    public int accountType;

    @Override
    public WxAccountTypeEnums getAccountType() {
        return WxAccountTypeEnums.getWxAccountType(accountType);
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}

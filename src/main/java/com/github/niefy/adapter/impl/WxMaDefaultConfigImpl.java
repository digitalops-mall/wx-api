package com.github.niefy.adapter.impl;

import com.github.niefy.adapter.WxConfig;
import com.github.niefy.constant.WxAccountTypeEnums;

public class WxMaDefaultConfigImpl extends cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl implements WxConfig {

    public int accountType;

    @Override
    public WxAccountTypeEnums getAccountType() {
        return WxAccountTypeEnums.getWxAccountType(accountType);
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}

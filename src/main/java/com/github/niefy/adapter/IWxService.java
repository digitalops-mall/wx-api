package com.github.niefy.adapter;

import com.github.niefy.modules.wx.entity.WxAccount;
import me.chanjar.weixin.common.error.WxErrorException;

public interface IWxService {



    public Integer switchover(String appid);

    public void switchoverTo(String appid);

    public String getOpenId(String appid, String code) throws WxErrorException;


    public void addAccountToRuntime(WxAccount entity);

    public void removeConfigStorage(String appid);

}

package com.github.niefy.adapter.impl;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.github.niefy.adapter.IWxService;
import com.github.niefy.adapter.WxConfig;
import com.github.niefy.constant.WxAccountTypeEnums;
import com.github.niefy.modules.wx.entity.WxAccount;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WxServiceImpl implements IWxService {


    @Autowired
    WxMaService wxMaService;
    @Autowired
    WxMpService wxMpService;

    @Override
    public Integer switchover(String appid) {
        WxConfig wxConfig = WxConfigContainer.getInstance().getWxConfigMap().get(appid);
        WxAccountTypeEnums accountType = wxConfig.getAccountType();
        switch (accountType) {
            case SUBSCRIPTION_ACCOUNT:
            case SERVICE_ACCOUNT:
                if(wxMpService.switchover(appid)) {
                    return WxAccountTypeEnums.SERVICE_ACCOUNT.getValue();
                }
            case MINI_PROGRAM:
                if(wxMaService.switchover(appid)){
                    return WxAccountTypeEnums.MINI_PROGRAM.getValue();
                }
        }
        return null;
    }

    @Override
    public void switchoverTo(String appid) {
        WxConfig wxConfig = WxConfigContainer.getInstance().getWxConfigMap().get(appid);
        WxAccountTypeEnums accountType = wxConfig.getAccountType();
        switch (accountType) {
            case SUBSCRIPTION_ACCOUNT:
            case SERVICE_ACCOUNT:
                wxMpService.switchoverTo(appid);
                return;
            case MINI_PROGRAM:
                wxMaService.switchoverTo(appid);
                return;
        }
    }

    @Override
    public String getOpenId(String appid, String code) throws WxErrorException {
        WxConfig wxConfig = WxConfigContainer.getInstance().getWxConfigMap().get(appid);
        WxAccountTypeEnums accountType = wxConfig.getAccountType();
        switch (accountType) {
            case SUBSCRIPTION_ACCOUNT:
            case SERVICE_ACCOUNT:
                WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
                return accessToken.getOpenId();
            case MINI_PROGRAM:
                WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
                return session.getOpenid();
        }
        return null;
    }

    @Override
    public void addAccountToRuntime(WxAccount entity) {
        String appid = entity.getAppid();
        WxConfig wxConfig = entity.toWxConfig();
        WxConfigContainer.getInstance().getWxConfigMap().put(appid,wxConfig);
        WxAccountTypeEnums accountType = wxConfig.getAccountType();
        switch (accountType) {
            case SUBSCRIPTION_ACCOUNT:
            case SERVICE_ACCOUNT:
                WxMpDefaultConfigImpl wxMpConfig = (WxMpDefaultConfigImpl)wxConfig;
                try {
                    wxMpService.addConfigStorage(appid,wxMpConfig);
                }catch (NullPointerException e){
                    log.info("需初始化configStorageMap...");
                    Map<String, WxMpConfigStorage> configStorages = new HashMap<>(4);
                    configStorages.put(appid,wxMpConfig);
                    wxMpService.setMultiConfigStorages(configStorages,appid);
                }
                return;
            case MINI_PROGRAM:
                WxMaDefaultConfigImpl wxMaConfig = (WxMaDefaultConfigImpl)wxConfig;
                try {
                    wxMaService.addConfig(appid, wxMaConfig);
                }catch (NullPointerException e){
                    log.info("需初始化configStorageMap...");
                    Map<String, WxMaConfig> configStorages = new HashMap<>(4);
                    configStorages.put(appid,wxMaConfig);
                    wxMaService.setMultiConfigs(configStorages,appid);
                }
                return;
        }
    }

    @Override
    public void removeConfigStorage(String appid) {
        WxConfig wxConfig = WxConfigContainer.getInstance().getWxConfigMap().get(appid);
        WxAccountTypeEnums accountType = wxConfig.getAccountType();
        switch (accountType) {
            case SUBSCRIPTION_ACCOUNT:
            case SERVICE_ACCOUNT:
                wxMpService.removeConfigStorage(appid);
                return;
            case MINI_PROGRAM:
                wxMaService.removeConfig(appid);
                return;
        }
    }
}

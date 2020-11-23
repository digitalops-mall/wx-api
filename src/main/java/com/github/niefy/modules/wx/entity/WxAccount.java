package com.github.niefy.modules.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.niefy.adapter.WxConfig;
import com.github.niefy.constant.WxAccountTypeEnums;
import lombok.Data;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 公众号账号
 * 
 * @author niefy
 * @date 2020-06-17 13:56:51
 */
@Data
@TableName("wx_account")
public class WxAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.INPUT)
	@NotEmpty(message = "appid不得为空")
	private String appid;
	/**
	 * 公众号名称
	 */
	@NotEmpty(message = "名称不得为空")
	private String name;
	/**
	 * 账号类型
	 */
	private int type;
	/**
	 * 认证状态
	 */
	private boolean verified;
	/**
	 * appsecret
	 */
	@NotEmpty(message = "appSecret不得为空")
	private String secret;
	/**
	 * token
	 */
	private String token;
	/**
	 * aesKey
	 */
	private String aesKey;

	public WxMpDefaultConfigImpl toWxMpConfigStorage(){
		WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
		configStorage.setAppId(appid);
		configStorage.setSecret(secret);
		configStorage.setToken(token);
		configStorage.setAesKey(aesKey);
		return configStorage;
	}

	public com.github.niefy.adapter.impl.WxMaDefaultConfigImpl toWxMaConfigStorageAdapter(){
		com.github.niefy.adapter.impl.WxMaDefaultConfigImpl configStorage = new com.github.niefy.adapter.impl.WxMaDefaultConfigImpl();
		configStorage.setAppid(appid);
		configStorage.setSecret(secret);
		configStorage.setToken(token);
		configStorage.setAesKey(aesKey);
		configStorage.setAccountType(type);
		return configStorage;
	}

	public com.github.niefy.adapter.impl.WxMpDefaultConfigImpl toWxMpConfigStorageAdapter(){
		com.github.niefy.adapter.impl.WxMpDefaultConfigImpl configStorage = new com.github.niefy.adapter.impl.WxMpDefaultConfigImpl();
		configStorage.setAppId(appid);
		configStorage.setSecret(secret);
		configStorage.setToken(token);
		configStorage.setAesKey(aesKey);
		configStorage.setAccountType(type);
		return configStorage;
	}

	public WxConfig toWxConfig() {
		WxAccountTypeEnums wxAccountType = WxAccountTypeEnums.getWxAccountType(this.type);
		switch (wxAccountType) {
			case SUBSCRIPTION_ACCOUNT:
			case SERVICE_ACCOUNT:
				return toWxMpConfigStorageAdapter();
			case MINI_PROGRAM:
				return toWxMaConfigStorageAdapter();
		}
		return null;
	}

}

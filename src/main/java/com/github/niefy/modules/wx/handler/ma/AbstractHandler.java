package com.github.niefy.modules.wx.handler.ma;

import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Binary Wang
 */
public abstract class AbstractHandler implements WxMaMessageHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

}

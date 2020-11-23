package com.github.niefy.modules.wx.handler.ma;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.MsgReplyService;
import com.github.niefy.modules.wx.service.WxMsgService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Oliver
 */
@Component
public class MaMsgHandler extends AbstractHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MsgReplyService msgReplyService;
    @Autowired
    WxMsgService wxMsgService;
    private static final String TRANSFER_CUSTOMER_SERVICE_KEY = "人工";

    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMessage,
                                    Map<String, Object> context, WxMaService wxMaService,
                                    WxSessionManager sessionManager) {

        String textContent = wxMessage.getContent();
        String fromUser = wxMessage.getFromUser();
        String appid = WxMpConfigStorageHolder.get();
        boolean autoReplyed = msgReplyService.tryAutoReply(appid,false, fromUser, textContent);
        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if (TRANSFER_CUSTOMER_SERVICE_KEY.equals(textContent) || !autoReplyed) {
            wxMsgService.addWxMsg(WxMsg.buildOutMsg(WxConsts.KefuMsgType.TRANSFER_CUSTOMER_SERVICE,fromUser,null));
            return WxMaXmlOutMessage.builder()
                .fromUserName(wxMessage.getToUser())
                .toUserName(fromUser).build();
        }
        return null;
    }

}

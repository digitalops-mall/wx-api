package com.github.niefy.modules.wx.handler.ma;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.github.niefy.modules.wx.service.MsgReplyService;
import com.github.niefy.modules.wx.service.WxUserService;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author oliver
 */
@Component
public class MaSubscribeHandler extends AbstractHandler {
    @Autowired
    MsgReplyService msgReplyService;
    @Autowired
    WxUserService userService;

    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMessage,
                                    Map<String, Object> context, WxMaService wxMaService,
                                    WxSessionManager sessionManager) {
        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser() + "，事件：" + wxMessage.getEvent());
        String appid = WxMaConfigHolder.get();
        this.logger.info("appid:{}",appid);
        userService.refreshUserInfo(wxMessage.getFromUser(),appid);
        msgReplyService.tryAutoReply(appid, true, wxMessage.getFromUser(), wxMessage.getEvent());
        return null;
    }

}

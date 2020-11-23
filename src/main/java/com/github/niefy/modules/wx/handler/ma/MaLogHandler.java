package com.github.niefy.modules.wx.handler.ma;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import com.github.niefy.common.utils.Json;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.WxMsgService;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author oliver
 */
@Component
public class MaLogHandler extends AbstractHandler {
    @Autowired
    WxMsgService wxMsgService;

    @Override
    public WxMaXmlOutMessage handle(WxMaMessage wxMessage,
                                    Map<String, Object> context, WxMaService wxMaService,
                                    WxSessionManager sessionManager) {
        try {
            this.logger.debug("\n接收到请求消息，内容：{}", Json.toJsonString(wxMessage));
            wxMsgService.addWxMsg(new WxMsg(wxMessage));
        } catch (Exception e) {
            this.logger.error("记录消息异常",e);
        }

        return null;
    }

}

package com.github.niefy.modules.wx.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.github.niefy.modules.wx.handler.ma.MaLogHandler;
import com.github.niefy.modules.wx.handler.ma.MaMsgHandler;
import com.github.niefy.modules.wx.handler.ma.MaSubscribeHandler;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@RequiredArgsConstructor
@Configuration
public class WxMaMessageRouterConfiguration {
    private final MaLogHandler maLogHandler;
    private final MaSubscribeHandler maSubscribeHandler;
    private final MaMsgHandler maMsgHandler;

    @Bean
    public WxMaMessageRouter maMessageRouter(WxMaService wxMaService) {
        final WxMaMessageRouter newRouter = new WxMaMessageRouter(wxMaService);
        newRouter
                .rule().handler(maLogHandler).next()
                .rule().async(false).content("订阅消息").handler(maSubscribeHandler).end()
                .rule().async(false).content("文本").handler(maMsgHandler).end()
                .rule().async(false).content("图片").handler(picHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();

        return newRouter;
    }


    private final WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            WxMediaUploadResult uploadResult = service.getMediaService()
                    .uploadMedia("image", "png",
                            ClassLoader.getSystemResourceAsStream("tmp.png"));
            service.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMessage.getFromUser())
                            .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    };

    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
        try {
            final File file = service.getQrcodeService().createQrcode("123", 430);
            WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
            service.getMsgService().sendKefuMsg(
                    WxMaKefuMessage
                            .newImageBuilder()
                            .mediaId(uploadResult.getMediaId())
                            .toUser(wxMessage.getFromUser())
                            .build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        return null;
    };
}

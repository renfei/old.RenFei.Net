package net.renfei.core.service.wechat;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.LogINOUT;
import net.renfei.core.entity.LogLevel;
import net.renfei.core.entity.wechat.TextMessage;
import net.renfei.core.entity.wechat.WeChatMessage;
import net.renfei.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
public class WeChatService extends BaseService {
    @Autowired
    private WeChatMessageService weChatMessageService;
    @Autowired
    private WeChatKeywordService weChatKeywordService;

    public boolean checkWeChat(HttpServletRequest request) {
        /**
         * 微信加密签名
         */
        String signature = request.getParameter("signature");
        /**
         * 随机字符串
         */
        String echostr = request.getParameter("echostr");
        /**
         * 时间戳
         */
        String timestamp = request.getParameter("timestamp");
        /**
         * 随机数
         */
        String nonce = request.getParameter("nonce");

        String[] str = {timestamp, nonce, renFeiConfig.getWECHAT_TOKEN()};
        //将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(str);
        StringBuilder sb = new StringBuilder();
        //将三个参数字符串拼接成一个字符串进行sha1加密
        for (String param : str) {
            sb.append(param);
        }
        //获取到加密过后的字符串
        String encryptStr = EncryptionUtil.encrypt("SHA1", sb.toString());
        //判断加密后的字符串是否与微信的签名一致
        return signature.equalsIgnoreCase(encryptStr);
    }

    public String weChatMessageHandelCoreService(HttpServletRequest request) {
        // 返回给微信服务器的消息,默认为null
        String respMessage = null;
        try {

            // 默认返回的文本消息内容
            String respContent = null;
            // xml分析
            // 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
            Map<String, String> map = weChatMessageService.parseXml(request);
            WeChatMessage weChatMessage = new WeChatMessage(map);
            logDBService.insertLogDB(LogLevel.WECHAT, LogINOUT.IN, weChatMessage.getFromUserName(), weChatMessage.getMessage());
            // 默认回复文本消息
            TextMessage textMessage = new TextMessage(weChatMessage);
            //验证消息来自微信官方
            if (!checkWeChat(request)) {
                textMessage.setContent("您的消息似乎不是来自微信官方服务器，所以未被处理。");
                return weChatMessageService.textMessageToXml(textMessage);
            }

            // 分析用户发送的消息类型，并作出相应的处理
            switch (weChatMessage.getMessageType()) {
                case TEXT:
                    respMessage = textMessageProcessor(weChatMessage, textMessage);
                    break;
                case IMAGE:
                    respContent = "我们已经收到您发送的图片：" + weChatMessage.getMessage() + "，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case VOICE:
                    return textMessageProcessor(weChatMessage, textMessage);
                case VIDEO:
                    respContent = "我们已经收到您发送的视频消息，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case LOCATION:
                    respContent = "我们已经收到您发送的位置信息：" + weChatMessage.getMessage() + "，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case LINK:
                    respContent = "我们已经收到您发送的网页信息：" + weChatMessage.getMessage() + "，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case EVENT:
                    break;
            }
            logDBService.insertLogDB(LogLevel.WECHAT, LogINOUT.OUT, weChatMessage.getFromUserName(), respMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            logDBService.insertLogDB(LogLevel.ERROR, LogINOUT.IN, e.getMessage());
            respMessage = null;
        }
        return respMessage;
    }

    /**
     * 文字类处理
     *
     * @param weChatMessage
     * @param textMessage
     * @return
     */
    private String textMessageProcessor(WeChatMessage weChatMessage, TextMessage textMessage) {
        if ("【收到不支持的消息类型，暂无法显示】".equals(weChatMessage.getMessage())) {
            textMessage.setContent("我们已经收到您发送的消息，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。");
            return weChatMessageService.textMessageToXml(textMessage);
        } else {
            return weChatKeywordService.talk(weChatMessage);
        }
    }
}

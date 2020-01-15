package net.renfei.core.service.wechat;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.wechat.TextMessage;
import net.renfei.core.entity.wechat.WeChatMessage;
import net.renfei.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class WeChatService extends BaseService {
    @Autowired
    private WeChatMessageService weChatMessageService;
    @Autowired
    private WeChatMessage weChatMessage;

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
            // 发送方账号
            String fromUserName = map.get("FromUserName");
            weChatMessage.setFromUserName(fromUserName);
            // 接受方账号（公众号）
            String toUserName = map.get("ToUserName");
            weChatMessage.setToUserName(toUserName);
            // 消息类型
            String msgType = map.get("MsgType");
            weChatMessage.setMessageType(msgType);
            // 默认回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(WeChatMessageService.RESP_MESSAGE_TYPE_TEXT);
            //验证消息来自微信官方
            if (!checkWeChat(request)) {
                textMessage.setContent("您的消息似乎不是来自微信官方服务器，所以未被处理。");
                return weChatMessageService.textMessageToXml(textMessage);
            }

            String msg = "";

            // 分析用户发送的消息类型，并作出相应的处理
            switch (msgType) {
                case WeChatMessageService.REQ_MESSAGE_TYPE_TEXT:
                    return textMessageProcessor(map.get("Content"), textMessage);
                case WeChatMessageService.REQ_MESSAGE_TYPE_IMAGE:
                    msg = map.get("PicUrl");
                    respContent = "我们已经收到您发送的图片：" + msg + "，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case WeChatMessageService.REQ_MESSAGE_TYPE_VOICE:
                    msg = map.get("Recognition");
                    respContent = "我们已经收到您发送的语音消息：\"" + msg + "\"，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case WeChatMessageService.REQ_MESSAGE_TYPE_VIDEO:
                    respContent = "我们已经收到您发送的视频消息，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case WeChatMessageService.REQ_MESSAGE_TYPE_LOCATION:
                    msg = map.get("Label");
                    respContent = "我们已经收到您发送的位置信息：" + msg + "，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case WeChatMessageService.REQ_MESSAGE_TYPE_LINK:
                    msg = map.get("Url");
                    respContent = "我们已经收到您发送的网页信息：" + msg + "，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。";
                    textMessage.setContent(respContent);
                    textMessage.setContent(respContent);
                    respMessage = weChatMessageService.textMessageToXml(textMessage);
                    break;
                case WeChatMessageService.REQ_MESSAGE_TYPE_EVENT:
                    // 事件推送(当用户主动点击菜单，或者扫面二维码等事件)
                    // 事件类型
                    String eventType = map.get("Event");
                    log.info("eventType------>" + eventType);
                    switch (eventType) {
                        case WeChatMessageService.EVENT_TYPE_SUBSCRIBE:
                            log.info("关注");
                            break;
                        case WeChatMessageService.EVENT_TYPE_UNSUBSCRIBE:
                            log.info("取消关注");
                            break;
                        case WeChatMessageService.EVENT_TYPE_SCAN:
                            log.info("扫描带参数二维码");
                            break;
                        case WeChatMessageService.EVENT_TYPE_LOCATION:
                            log.info("上报地理位置");
                            break;
                        case WeChatMessageService.EVENT_TYPE_CLICK:
                            // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                            String eventKey = map.get("EventKey");
                            log.info("eventKey------->" + eventKey);
                            break;
                        case WeChatMessageService.EVENT_TYPE_VIEW:
                            log.info("处理自定义菜单URI视图");
                            break;
                    }
                    break;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            respMessage = null;
        }
        return respMessage;
    }

    private String textMessageProcessor(String message, TextMessage textMessage) {
        if ("【收到不支持的消息类型，暂无法显示】".equals(message)) {
            textMessage.setContent("我们已经收到您发送的消息，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。");
            return weChatMessageService.textMessageToXml(textMessage);
        } else {
            //[TODO]此处根据情况改变返回类型，默认是文字消息
            textMessage.setContent("你发送的消息是：" + message);
            return weChatMessageService.textMessageToXml(textMessage);
        }
    }
}

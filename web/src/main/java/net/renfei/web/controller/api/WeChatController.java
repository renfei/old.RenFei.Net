package net.renfei.web.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.service.wechat.WeChatService;
import net.renfei.web.baseclass.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@RequestMapping("/api/wechat")
public class WeChatController extends BaseController {
    @Autowired
    private WeChatService weChatService;

    /**
     * 验证微信消息
     *
     * @param request
     * @return
     */
    @GetMapping(value = "access")
    public String checkWxMsg(HttpServletRequest request) {
        String echostr = request.getParameter("echostr");
        //判断加密后的字符串是否与微信的签名一致
        if (weChatService.checkWeChat(request)) {
            return echostr;
        } else {
            log.warn("微信消息验证失败！");
            return null;
        }
    }

    @PostMapping("access")
    public String getWeiXinMessage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        request.setCharacterEncoding("UTF-8");
        // 在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        response.setCharacterEncoding("UTF-8");
        return weChatService.weChatMessageHandelCoreService(request);
    }
}

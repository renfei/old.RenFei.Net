package net.renfei.web.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.renfei.core.entity.IPDTO;
import net.renfei.dao.entity.ShortUrl;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.APIResult;
import net.renfei.web.entity.ShortUrlVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.StringWriter;

@Slf4j
@Controller
@RequestMapping("/kitbox")
public class KitboxController extends BaseController {
    @RequestMapping("")
    public ModelAndView getKitboxIndex(ModelAndView mv) {
        setHead(mv, "开发者工具箱 - Kitbox", "免费的开发者工具箱小工具，工欲善其事，必先利其器。");
        mv.setViewName("kitbox/index");
        return mv;
    }

    @RequestMapping("ip")
    public ModelAndView getIPInfo(ModelAndView mv) {
        String myip = ipService.getIpAddr(localRequest.get());
        IPDTO ipdto = ipService.getIPInfor(myip);
        mv.addObject("myip", myip);
        mv.addObject("ipdto", ipdto);
        setHead(mv, "IP地址信息查询工具 - 开发者工具箱 - Kitbox",
                "IP地址信息查询工具，开放服务接口实现IP信息查询，查询IP地址所属地理位置",
                "IP,地址,信息,查询,工具,地理,位置");
        mv.setViewName("kitbox/ipinfo");
        return mv;
    }

    @RequestMapping("digtrace")
    public ModelAndView getDigTrace(ModelAndView mv) {
        setHead(mv, "在线Dig+trace命令检测DNS状态工具 - 开发者工具箱 - Kitbox",
                "域名解析信息查询工具，开放服务接口实现dig+trace域名解析过程查询",
                "域名,解析,信息,查询,工具,Dig,trace,DNS");
        mv.setViewName("kitbox/digtrace");
        return mv;
    }

    @RequestMapping({"uuid", "guid", "UUID", "GUID"})
    public ModelAndView getUUID(ModelAndView mv) {
        setHead(mv, "UUID/GUID 在线批量生成工具 - 开发者工具箱 - Kitbox",
                "在线批量生成 UUID/GUID 工具",
                "UUID,GUID,在线,批量,生成,工具");
        mv.setViewName("kitbox/uuid");
        return mv;
    }

    @RequestMapping({"getmyip"})
    public ModelAndView getmyip(ModelAndView mv) {
        setHead(mv, "公网IP获取工具 - 开发者工具箱 - Kitbox",
                "公网IP获取工具支持Linux、Windows、API",
                "IP,公网,出口,地址,工具");
        mv.setViewName("kitbox/getmyip");
        return mv;
    }

    @RequestMapping({"freemarkerTest", "FtlTest"})
    public ModelAndView freemarkerTest(ModelAndView mv) {
        setHead(mv, "FreeMarker在线测试工具 - 开发者工具箱 - Kitbox",
                "FreeMarker在线测试工具",
                "FreeMarker,ftl,在线,测试,工具");
        mv.setViewName("kitbox/freemarkerTest");
        return mv;
    }

    @RequestMapping("md5")
    public ModelAndView md5Tools(ModelAndView mv) {
        setHead(mv, "MD5在线加密工具 - 开发者工具箱 - Kitbox",
                "MD5在线加密工具，对字符串进行MD5计算得出MD5加密字符串",
                "MD5,在线,加密,解密,字符串");
        mv.setViewName("kitbox/md5");
        return mv;
    }

    @RequestMapping("sha1")
    public ModelAndView sha1Tools(ModelAndView mv) {
        setHead(mv, "SHA-1在线加密工具 - 开发者工具箱 - Kitbox",
                "SHA-1 散列函数加密算法输出的散列值为40位十六进制数字串，可用于验证信息的一致性，防止被篡改。本页面的 SHA-1 在线加密工具可对字符串进行 SHA-1 加密，并可转换散列值中字母的大小写。",
                "SHA-1,在线,加密,解密,字符串");
        mv.setViewName("kitbox/sha1");
        return mv;
    }

    @RequestMapping("sha256")
    public ModelAndView sha256Tools(ModelAndView mv) {
        setHead(mv, "SHA-256在线加密工具 - 开发者工具箱 - Kitbox",
                "SHA-256 散列函数加密算法输出的散列值可用于验证信息的一致性，防止被篡改。本页面的 SHA-256 在线加密工具可对字符串进行 SHA-256 加密，并可转换散列值中字母的大小写。",
                "SHA-256,在线,加密,解密,字符串");
        mv.setViewName("kitbox/sha256");
        return mv;
    }

    @RequestMapping("sha512")
    public ModelAndView sha512Tools(ModelAndView mv) {
        setHead(mv, "SHA-512在线加密工具 - 开发者工具箱 - Kitbox",
                "SHA-512 散列函数加密算法输出的散列值可用于验证信息的一致性，防止被篡改。本页面的 SHA-512 在线加密工具可对字符串进行 SHA-512 加密，并可转换散列值中字母的大小写。",
                "SHA-512,在线,加密,解密,字符串");
        mv.setViewName("kitbox/sha512");
        return mv;
    }

    @RequestMapping("url16")
    public ModelAndView url16Tools(ModelAndView mv) {
        setHead(mv, "URL网址16进制加密工具 - 开发者工具箱 - Kitbox",
                "RL编码形式表示的ASCII字符(十六进制格式)。把URL网址转换成16进制代码形式,加密后可直接复制到地址栏访问。",
                "URL,网址,加密,16进制,hex");
        mv.setViewName("kitbox/url16");
        return mv;
    }

    @RequestMapping({"qrcode", "QRCode"})
    public ModelAndView qrCode(ModelAndView mv) {
        setHead(mv, "二维码在线生成工具 - 开发者工具箱 - Kitbox",
                "二维码在线生成工具",
                "二维码,qrcode,在线,生成,工具");
        mv.setViewName("kitbox/qrcode");
        return mv;
    }

    @RequestMapping({"ShortUrl", "ShortURL"})
    public ModelAndView shortUrl(ModelAndView mv) {
        setHead(mv, "短网址在线生成工具 - 开发者工具箱 - Kitbox",
                "短网址在线生成工具",
                "短网址,Short,Url,生成,工具");
        mv.setViewName("kitbox/ShortURL");
        return mv;
    }

    @PostMapping("ShortURL/do")
    @ResponseBody
    public APIResult setShortUrl(String url) {
        if (stringUtil.isEmpty(url)) {
            return APIResult.fillResult(false, "Url不合法");
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            try {
                ShortUrl shortUrl = shortUrlService.createShortUrl(url, null);
                if (shortUrl != null) {
                    ShortUrlVO shortUrlVO = new ShortUrlVO();
                    BeanUtils.copyProperties(shortUrl, shortUrlVO);
                    shortUrlVO.setShortUrl(ShortUrlVO.BASE_DOMAIN + shortUrl.getShortUrl());
                    return APIResult.fillResult(true, "Success!", shortUrlVO);
                } else {
                    return APIResult.fillResult(false, "内部服务错误");
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                return APIResult.fillResult(false, "内部服务错误");
            }

        } else {
            return APIResult.fillResult(false, "Url不合法");
        }
    }

    @RequestMapping("ShortURL/do/{url}")
    @ResponseBody
    public APIResult getShortUrl(@PathVariable("url") String url) {
        if (stringUtil.isEmpty(url)) {
            return APIResult.fillResult(false, "Url不合法");
        }
        try {
            ShortUrl shortUrl = shortUrlService.getShortUrl(url);
            if (shortUrl != null) {
                ShortUrlVO shortUrlVO = new ShortUrlVO();
                BeanUtils.copyProperties(shortUrl, shortUrlVO);
                shortUrlVO.setShortUrl(shortUrl.getShortUrl());
                return APIResult.fillResult(true, "Success!", shortUrlVO);
            } else {
                return APIResult.fillResult(false, "短网址不存在");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, "内部服务错误");
        }
    }

    @ResponseBody
    @PostMapping("freemarkerTest/post")
    public APIResult getContentByFreeMarkerAndBean(String ftl, String beanJson) {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());
        freemarker.cache.StringTemplateLoader templateLoader = new freemarker.cache.StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        try {
            freemarker.template.Template template = new freemarker.template.Template("freemarkerTest", ftl, configuration);
            StringWriter stringWriter = new StringWriter();
            Object object = JSON.parseObject(beanJson, Object.class);
            template.process(object, stringWriter);
            return APIResult.fillResult(true, "", stringWriter.toString());
        } catch (Exception ex) {
            return APIResult.fillResult(false, ex.getMessage(), ex.getMessage());
        }
    }
}

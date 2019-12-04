package net.renfei.core.service.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.renfei.core.config.RenFeiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 百度站长资源站服务
 */
@Slf4j
@Service
public class BaiduZiyuan {
    private static final String linkSubmitUrl = "http://data.zz.baidu.com/urls?site=%s&token=%s";
    private static final String mobileLinkSubmitUrl = "http://data.zz.baidu.com/urls?appid=%s&token=%s&type=realtime";

    @Autowired
    private RenFeiConfig renFeiConfig;

    /**
     * 整体发送
     *
     * @param link
     * @return
     */
    public boolean sentLink(String link) {
        return sentLinkSubmit(link) && sentMobileLink(link);
    }

    /**
     * 百度站长资源提交
     *
     * @param link
     * @return
     */
    public boolean sentLinkSubmit(String link) {
        try {
            JSONObject jsonObject = JSON.parseObject(
                    sent(link,
                            String.format(linkSubmitUrl,
                                    renFeiConfig.getBAIDU_ZIYUAN_SITE(),
                                    renFeiConfig.getBAIDU_ZIYUAN_TOKEN())
                    ).getBody());
            return jsonObject.get("success") != null;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 百度移动平台专区资源提交
     *
     * @param link
     */
    public boolean sentMobileLink(String link) {
        try {
            return sent(link,
                    String.format(mobileLinkSubmitUrl,
                            renFeiConfig.getBAIDU_ZIYUAN_MOBILE_APPID(),
                            renFeiConfig.getBAIDU_ZIYUAN_MOBILE_TOKEN())
            ).getStatusCodeValue() == 200;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 发送请求
     *
     * @param link
     * @param postUrl
     * @return
     */
    private ResponseEntity<String> sent(String link, String postUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>(link, headers);
        return restTemplate.postForEntity(
                postUrl,
                request, String.class);
    }
}

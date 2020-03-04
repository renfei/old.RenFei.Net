package net.renfei.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "renfei")
public class RenFeiConfig {
    private String totpseed;
    private String jwtprivatekey;
    private String jwtpublickey;
    private String jwtaeskey;
    private String GREEN_REGION_ID;
    private String ALIYUN_ACCESS_KEY_ID;
    private String ALIYUN_ACCESS_KEY_SECRET;
    private String ALIYUN_OSS_ENDPOINT;
    private String ALIYUN_OSS_BUCKENAME;
    private String ALIYUN_OSS_DOWNLOAD_BUCKENAME;
    private String ALIYUN_OSS_DOWNLOAD_HOST;
    private String BAIDU_ZIYUAN_SITE;
    private String BAIDU_ZIYUAN_TOKEN;
    private String BAIDU_ZIYUAN_MOBILE_APPID;
    private String BAIDU_ZIYUAN_MOBILE_TOKEN;
    private String WECHAT_APP_ID;
    private String WECHAT_APP_SECRET;
    private String WECHAT_TOKEN;
    private String GOOGLE_ADS;
}

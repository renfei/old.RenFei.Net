package net.renfei.core.service.baidu;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaiduZiyuanTest extends TestApplication {
    @Autowired
    private BaiduZiyuan baiduZiyuan;

    @Test
    public void sent() {
//        log.info(baiduZiyuan.sentLink("https://www.renfei.net/posts/1003291") ? "true" : "false");
    }
}

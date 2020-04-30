package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ShortUrlServiceTest extends TestApplication {
    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    public void test() {
//        for (int i = 0; i < 10000; i++) {
//            log.info(shortUrlService.createShortUrl(i + "", null).getShortUrl());
//        }
    }
}

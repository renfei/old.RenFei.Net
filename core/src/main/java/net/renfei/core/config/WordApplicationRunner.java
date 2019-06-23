package net.renfei.core.config;

import net.renfei.core.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class WordApplicationRunner implements ApplicationRunner {
    @Autowired
    private WordService wordService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //第一次启动需要调用一下分词服务，让分词字典加载，避免后续调用响应时间过大
        wordService.getWords("第一次启动需要调用一下分词服务，让分词字典加载");
    }
}

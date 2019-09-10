package net.renfei.core.service.aliyun;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AliyunGreenTest extends TestApplication {
    @Autowired
    private AliyunGreen aliyunGreen;

    @Test
    public void textScan(){
//        aliyunGreen.textScan("暴恐检测示例");
    }
}

package net.renfei.core.service.aliyun;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AliyunOSSTest extends TestApplication {
    @Autowired
    private AliyunOSS aliyunOSS;

    @Test
    public void test(){
//        log.info(aliyunOSS.getPresignedUrl("centos/CentOS-6.10-i386-LiveDVD.torrent"));
    }
}

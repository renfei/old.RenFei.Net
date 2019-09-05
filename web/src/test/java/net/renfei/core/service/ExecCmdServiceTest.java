package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ExecCmdServiceTest extends TestApplication {
    @Autowired
    private DomainNameService domainNameService;
    @Test
    public void execCmdTest(){
        log.info((String) domainNameService.execDigTrace("renfei.net").getData());
    }
}

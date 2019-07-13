package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TotpServiceTest extends TestApplication {
    @Autowired
    private TotpService totpService;

    @Test
    public void Test() {
        String secret = totpService.generateSecretKey();
        String totp = totpService.genTotpString("renfei", secret);
    }
}

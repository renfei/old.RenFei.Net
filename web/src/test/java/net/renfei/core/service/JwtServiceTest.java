package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import net.renfei.core.entity.TokenSubject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JwtServiceTest extends TestApplication {
    @Autowired
    private JwtService jwtService;

    @Test
    public void myJwtTest() throws Exception {
        log.info("Test JWT Start.");
        TokenSubject tokenSubject = new TokenSubject();
        tokenSubject.setAccount("renfei");
        String token = jwtService.getToken(tokenSubject, "abc");
        Assert.assertTrue(jwtService.validateToken(token));
        TokenSubject newTokenSubject = jwtService.readToken(token, "abc");
        Assert.assertEquals(tokenSubject, newTokenSubject);
        newTokenSubject = jwtService.readToken(token, "abc2");
        Assert.assertNull(newTokenSubject);
        log.info("Test JWT End.");
    }
}

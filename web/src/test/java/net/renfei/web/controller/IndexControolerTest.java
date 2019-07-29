package net.renfei.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
public class IndexControolerTest extends TestApplication {
    @Test
    public void index() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/"));
            mockMvc.perform(MockMvcRequestBuilders.get("/?page={page}", "2"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}

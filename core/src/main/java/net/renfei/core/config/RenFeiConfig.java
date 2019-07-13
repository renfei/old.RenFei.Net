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
}

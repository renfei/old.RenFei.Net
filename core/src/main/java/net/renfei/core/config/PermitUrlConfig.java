package net.renfei.core.config;

import org.springframework.stereotype.Component;

@Component
public class PermitUrlConfig {
    private static final String[] permitUrl = new String[]{
            "/api/comments",
            "/api/open/",
            "/api/security/secretkey",
            "/api/auth/signin",
            "/api/wechat"
    };

    public boolean permit(String url) {
        if (url.startsWith("/api/")) {
            for (String purl : permitUrl
            ) {
                if (url.startsWith(purl)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }
}

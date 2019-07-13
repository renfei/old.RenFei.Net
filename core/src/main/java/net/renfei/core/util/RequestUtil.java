package net.renfei.core.util;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestUtil extends BaseService {
    @Autowired
    private IpService ipService;

    public String getClientDigest(HttpServletRequest request) {
        String ip = ipService.getIpAddr(request);
        String userAgent = request.getHeader("user-agent");
        if (stringUtil.isEmpty(userAgent)) {
            return md5Util.encodeByMd5(ip);
        } else {
            return md5Util.encodeByMd5(userAgent + "-" + ip);
        }
    }
}

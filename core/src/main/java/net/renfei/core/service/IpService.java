package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.IPDTO;
import net.renfei.dao.entity.IPV4DO;
import net.renfei.dao.entity.IPV4DOExample;
import net.renfei.dao.entity.IPV6DO;
import net.renfei.dao.entity.IPV6DOExample;
import net.renfei.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class IpService extends BaseService {
    private StringUtil stringUtil = new StringUtil();

    /**
     * 获取来访的IP地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String remoteAddress = "";
            if (request != null) {
                //来自cloudflare的映射，自定义的
                remoteAddress = request.getHeader("Cf-Connecting-IP");
                if (remoteAddress == null || "".equals(remoteAddress)) {
                    //来自AWS的映射，自定义的
                    remoteAddress = request.getHeader("AWS-APIGateway-sourceIp");
                    if (remoteAddress == null || "".equals(remoteAddress)) {
                        //来自Nginx的映射
                        remoteAddress = request.getHeader("X-Forwarded-For");
                        if (remoteAddress == null || "".equals(remoteAddress)) {
                            remoteAddress = request.getRemoteAddr();
                        }
                    }
                }
            }
            remoteAddress = remoteAddress != null && remoteAddress.contains(",")
                    ? remoteAddress.split(",")[0]
                    : remoteAddress;
            return remoteAddress;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * 获取IP的信息
     *
     * @param ip
     * @return
     */
    public IPDTO getIPInfor(String ip) {
        IPDTO ipdto = new IPDTO();
        BigInteger bIp;
        try {
            bIp = stringUtil.stringToBigInt(ip);
        } catch (Exception e) {
            ipdto.setIp("Illegal IP address");
            ipdto.setIpBigInt(null);
            return ipdto;
        }
        if (BigInteger.valueOf(4294967295L).compareTo(bIp) == 1) {
            //IPv4
            IPV4DOExample ipv4DOExample = new IPV4DOExample();
            ipv4DOExample.createCriteria()
                    .andIpFromLessThanOrEqualTo(bIp)
                    .andIpToGreaterThanOrEqualTo(bIp);
            List<IPV4DO> ipv4DOS = ipv4DOMapper.selectByExample(ipv4DOExample);
            if (ipv4DOS != null && ipv4DOS.size() > 0) {
                ipdto = ejbGenerator.convert(ipv4DOS.get(0), IPDTO.class);
                ipdto.setIp(ip);
                ipdto.setIpBigInt(bIp);
                return ipdto;
            } else {
                return null;
            }
        } else {
            //IPv6
            IPV6DOExample ipv6DOExample = new IPV6DOExample();
            ipv6DOExample.createCriteria()
                    .andIpFromLessThanOrEqualTo(new BigDecimal(bIp))
                    .andIpToGreaterThanOrEqualTo(new BigDecimal(bIp));
            List<IPV6DO> ipv6DOS = ipv6DOMapper.selectByExample(ipv6DOExample);
            if (ipv6DOS != null && ipv6DOS.size() > 0) {
                ipdto = ejbGenerator.convert(ipv6DOS.get(0), IPDTO.class);
                ipdto.setIp(ip);
                ipdto.setIpBigInt(bIp);
                return ipdto;
            } else {
                return null;
            }
        }
    }

}

package net.renfei.web.controller.api.open;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.renfei.core.entity.IPDTO;
import net.renfei.core.service.DomainNameService;
import net.renfei.core.service.IpService;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open")
@Api(description = "开放接口服务", tags = "Open API Service")
public class OpenServiceController extends BaseRestController {
    @Autowired
    private IpService ipService;
    @Autowired
    private DomainNameService domainNameService;

    @GetMapping("ipinfo/{ip}")
    @ApiOperation(
            value = "IP地址信息查询服务",
            notes = "使用该接口可以用于查询IP地址的物理信息",
            tags = "Open API Service"
    )
    public APIResult getApiInfo(@PathVariable(value = "ip") String ip) {
        IPDTO ipdto = ipService.getIPInfor(ip);
        if (ipdto != null) {
            if (ipdto.getIpBigInt() == null) {
                return APIResult.fillResult(false, ipdto.getIp());
            }
            return APIResult.fillResult(true, "Success!", ipdto);
        }
        return APIResult.fillResult(false);
    }

    @GetMapping("/dns/dig/{domain}")
    @ApiOperation(
            value = "域名 dig+trace",
            notes = "使用该接口可以查询域名的解析过程",
            tags = "Open API Service"
    )
    public net.renfei.core.entity.APIResult getDomainDigTrace(@PathVariable(value = "domain") String domain) {
        return domainNameService.execDigTrace(domain);
    }
}

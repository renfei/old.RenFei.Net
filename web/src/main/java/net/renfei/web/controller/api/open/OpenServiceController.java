package net.renfei.web.controller.api.open;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.renfei.core.entity.IPDTO;
import net.renfei.core.service.DomainNameService;
import net.renfei.core.service.IpService;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/uuid")
    @ApiOperation(
            value = "UUID/GUID 生成接口",
            notes = "批量生成 UUID/GUID",
            tags = "Open API Service"
    )
    public APIResult getUUID(@RequestParam(value = "quantity", required = false) String quantity,
                             @RequestParam(value = "upperCase", required = false) Boolean upperCase,
                             @RequestParam(value = "hyphen", required = false) Boolean hyphen) {
        Long lQuantity = 0L;
        if (stringUtil.isEmpty(quantity)) {
            quantity = "1";
        }
        try {
            lQuantity = Long.parseLong(quantity);
        } catch (NumberFormatException nfe) {
            return APIResult.fillResult(false, "数量(quantity)传入异常");
        }
        if (lQuantity <= 0) {
            return APIResult.fillResult(false, "数量(quantity)传入异常");
        }
        if (upperCase == null) {
            upperCase = true;
        }
        if (hyphen == null) {
            hyphen = true;
        }
        List<String> stringUUID = new ArrayList<>();
        for (; lQuantity > 0; lQuantity--) {
            String uuid = UUID.randomUUID().toString();
            if (upperCase) {
                uuid = uuid.toUpperCase();
            } else {
                uuid = uuid.toLowerCase();
            }
            if (!hyphen) {
                uuid = uuid.replace("-", "");
            }
            stringUUID.add(uuid);
        }
        return APIResult.fillResult(true, "", stringUUID);
    }
}

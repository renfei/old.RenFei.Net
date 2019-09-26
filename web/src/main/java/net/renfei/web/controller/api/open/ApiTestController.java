package net.renfei.web.controller.api.open;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/api")
@Api(description = "开放接口测试", tags = "Open API Test")
public class ApiTestController extends BaseRestController {
    @GetMapping("test")
    @ApiOperation(
            value = "开放接口网络连通性测试",
            notes = "使用该接口可以用于测试你与接口的网络连通性",
            tags = "Open API Test"
    )
    public APIResult getApiInfo() {
        return APIResult.fillResult(true);
    }
}

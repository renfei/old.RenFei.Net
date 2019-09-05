package net.renfei.web.controller.api;

import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/menu")
public class MenuController extends BaseRestController {
    @GetMapping("")
    public APIResult getMenus() {
        APIResult apiResult = APIResult.fillResult(true, "", sysMenuService.getMenuByAccount(account));
        return apiResult;
    }
}

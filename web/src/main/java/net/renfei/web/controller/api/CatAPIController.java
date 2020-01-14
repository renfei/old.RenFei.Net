package net.renfei.web.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.service.CategorService;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/cat")
public class CatAPIController extends BaseRestController {
    @Autowired
    private CategorService categorService;

    @GetMapping("")
    public APIResult getCat(Long type) {
        try {
            return APIResult.fillResult(true, "", categorService.getAllCategoryByType(type));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }
}

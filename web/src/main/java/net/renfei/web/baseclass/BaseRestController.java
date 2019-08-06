package net.renfei.web.baseclass;

import net.renfei.util.StringUtil;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseRestController {
    protected StringUtil stringUtil = new StringUtil();
}

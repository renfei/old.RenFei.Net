package net.renfei.core.baseclass;

import net.renfei.core.config.RenFeiConfig;
import net.renfei.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService extends BaseClass {
    @Autowired
    protected RenFeiConfig renFeiConfig;
    @Autowired
    protected MD5Util md5Util;
    protected static int DEFAULT_PAGE = 1;
    protected static int DEFAULT_ROWS = 10;

    protected int convertPage(String page) {
        return stringUtil.convertInt(page, DEFAULT_PAGE);
    }

    protected int convertRows(String rows) {
        return stringUtil.convertInt(rows, DEFAULT_ROWS);
    }
}

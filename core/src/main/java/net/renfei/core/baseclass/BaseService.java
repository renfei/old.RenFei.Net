package net.renfei.core.baseclass;

import org.springframework.stereotype.Service;

@Service
public class BaseService extends BaseClass {
    protected static int DEFAULT_PAGE = 1;
    protected static int DEFAULT_ROWS = 10;

    protected int convertPage(String page) {
        return stringUtil.convertInt(page, DEFAULT_PAGE);
    }

    protected int convertRows(String rows) {
        return stringUtil.convertInt(rows, DEFAULT_ROWS);
    }
}

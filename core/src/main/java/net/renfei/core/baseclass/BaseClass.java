package net.renfei.core.baseclass;

import lombok.extern.slf4j.Slf4j;
import net.renfei.common.EJBGenerator;
import net.renfei.core.config.RenFeiConfig;
import net.renfei.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 全局基类
 */
@Slf4j
public class BaseClass {
    protected StringUtil stringUtil = new StringUtil();
    @Autowired
    protected EJBGenerator ejbGenerator = new EJBGenerator();
    @Autowired
    protected RenFeiConfig renFeiConfig;
}

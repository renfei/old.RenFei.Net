package net.renfei.core.baseclass;

import lombok.extern.slf4j.Slf4j;
import net.renfei.common.EJBGenerator;
import net.renfei.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaseClass {
    protected StringUtil stringUtil = new StringUtil();
    @Autowired
    protected EJBGenerator ejbGenerator = new EJBGenerator();
}

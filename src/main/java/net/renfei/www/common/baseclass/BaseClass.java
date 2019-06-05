package net.renfei.www.common.baseclass;

import lombok.extern.slf4j.Slf4j;
import net.renfei.www.common.EJBGenerator;
import net.renfei.www.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaseClass {
    @Autowired
    protected StringUtil stringUtil;
    @Autowired
    protected EJBGenerator ejbGenerator = new EJBGenerator();
}

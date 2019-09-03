package net.renfei.web.baseclass;

import net.renfei.core.service.AccountService;
import net.renfei.core.service.JwtService;
import net.renfei.core.service.SecretKeyService;
import net.renfei.core.service.SysMenuService;
import net.renfei.dao.entity.AccountDO;
import net.renfei.util.RSAUtils;
import net.renfei.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class BaseRestController {
    @Autowired
    protected RSAUtils rsaUtils;
    @Autowired
    protected SecretKeyService secretKeyService;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected SysMenuService sysMenuService;
    protected StringUtil stringUtil = new StringUtil();
    protected AccountDO account;
    /**
     * 线程绑定Request对象
     */
    protected ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<>();

    /**
     * 线程绑定Response对象
     */
    protected ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<>();

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param request
     * @param response
     * @param mv
     */
    @ModelAttribute
    public void mdeolAttribute(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
        account = accountService.getAccountByToken(jwtService.getJwtFromRequest(request));
    }
}

package net.renfei.web.controller.api;

import net.renfei.core.entity.SignInDTO;
import net.renfei.core.service.AccountService;
import net.renfei.core.util.RequestUtil;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.APIResult;
import net.renfei.web.entity.SignInVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RequestUtil requestUtil;

    @PostMapping("signin")
    public APIResult signin(SignInVO signInVO, HttpServletRequest request) {
        if (stringUtil.isEmpty(request.getHeader("uuid"))) {
            return APIResult.fillResult(false, "Head UUID missing");
        }
        SignInDTO signInDTO = ejbGenerator.convert(signInVO, SignInDTO.class);
        signInDTO.setUuid(request.getHeader("uuid"));
        signInDTO.setAudience(requestUtil.getClientDigest(request));
        signInDTO = accountService.signIn(signInDTO);
        APIResult apiResult = APIResult.fillResult(signInDTO.getSuccess(), signInDTO.getMessage(), signInDTO.getData());
        apiResult.setCode(signInDTO.getCode());
        return apiResult;
    }
}

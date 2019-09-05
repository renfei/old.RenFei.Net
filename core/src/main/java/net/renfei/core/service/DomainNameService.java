package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class DomainNameService extends BaseService {
    @Autowired
    private ExecCmdService execCmdService;

    public APIResult execDigTrace(String domain) {
        APIResult apiResult = APIResult.fillResult();
        if (checkDomainName(domain)) {
            try {
                apiResult.setSuccess(true);
                apiResult.setCode(200);
                apiResult.setMessage("Success!");
                apiResult.setData(execCmdService.execCmd("dig " + domain.trim() + " +trace"));
            } catch (IOException e) {
                apiResult.setSuccess(false);
                apiResult.setCode(500);
                apiResult.setMessage("Internal server error.");
                log.error(e.getMessage(), e);
            }
        } else {
            apiResult.setSuccess(false);
            apiResult.setMessage("Incorrect format of domain name.");
        }
        return apiResult;
    }

    public boolean checkDomainName(String domain) {
        String regex = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$";
        return domain.matches(regex);
    }
}

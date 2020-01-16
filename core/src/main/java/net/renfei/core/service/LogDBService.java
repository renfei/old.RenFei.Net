package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.entity.LogINOUT;
import net.renfei.core.entity.LogLevel;
import net.renfei.dao.entity.LogDO;
import net.renfei.dao.persistences.LogDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class LogDBService {
    @Autowired
    private LogDOMapper logDOMapper;
    @Autowired
    private IpService ipService;

    @Async
    public void insertLogDB(LogLevel level, LogINOUT inout, String user, String value) {
        LogDO logDO = new LogDO();
        logDO.setLogValue(value);
        logDO.setLevel(level.getLevel());
        logDO.setInorout(inout.getInOut());
        insertLogDB(logDO);
    }

    @Async
    public void insertLogDB(LogLevel level, LogINOUT inout, String value) {
        insertLogDB(level, inout, null, value);
    }

    /**
     * Log ACCESS To DataBase
     *
     * @param request
     * @param response
     * @return
     */
    @Async
    public void insertLogDB(HttpServletRequest request, HttpServletResponse response) {
        LogDO logDO = convert(request, response);
        logDO.setLevel(LogLevel.ACCESS.getLevel());
        logDO.setInorout(LogINOUT.IN.getInOut());
        try {
            logDO.setLogValue(null);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        insertLogDB(logDO);
    }

    @Async
    public void insertLogDB(LogDO logDO) {
        logDO.setUuid(UUID.randomUUID().toString());
        logDO.setDatetime(new Date());
        logDOMapper.insertSelective(logDO);
    }

    private LogDO convert(HttpServletRequest request) {
        return convert(request, null);
    }

    private LogDO convert(HttpServletRequest request, HttpServletResponse response) {
        LogDO logDO = new LogDO();
        logDO.setRemoteIp(ipService.getIpAddr(request));
        logDO.setRemoteUser(request.getRemoteUser());
        logDO.setRemoteAgent(request.getHeader("user-agent"));
        logDO.setRequestUrl(request.getServletPath());
        logDO.setRequestMethod(request.getMethod());
        if (response != null) {
            logDO.setStatusCode(response.getStatus());
        }
        return logDO;
    }
}

package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.SignInDTO;
import net.renfei.core.entity.TokenSubject;
import net.renfei.core.entity.UserDTO;
import net.renfei.dao.entity.AccountDO;
import net.renfei.dao.entity.AccountDOExample;
import net.renfei.dao.entity.TokenDO;
import net.renfei.dao.entity.TokenDOExample;
import net.renfei.dao.persistences.AccountDOMapper;
import net.renfei.dao.persistences.TokenDOMapper;
import net.renfei.util.PasswordStorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AccountService extends BaseService {
    @Autowired
    private AccountDOMapper accountDOMapper;
    @Autowired
    private TokenDOMapper tokenDOMapper;
    @Autowired
    private SecretKeyService secretKeyService;
    @Autowired
    private PasswordStorageUtils passwordStorageUtils;
    @Autowired
    private TotpService totpService;
    @Autowired
    private JwtService jwtService;

    public SignInDTO signIn(SignInDTO signInDTO) {
        //对账户密码等进行解密
        String account = secretKeyService.dataDecryptionByCryptoJS(signInDTO.getAccount(), signInDTO.getUuid());
        String password = secretKeyService.dataDecryptionByCryptoJS(signInDTO.getPassword(), signInDTO.getUuid());
        //先根据用户名查找用户
        AccountDOExample accountDOExample = new AccountDOExample();
        accountDOExample.createCriteria()
                .andAccountEqualTo(account);
        List<AccountDO> accountDOS = accountDOMapper.selectByExample(accountDOExample);
        if (accountDOS != null && accountDOS.size() > 0) {
            AccountDO accountDO = accountDOS.get(0);
            //检查用户的状态
            if (accountDO.getStatus() > 0) {
                //检查是否被锁定
                if (accountDO.getLockTime() != null) {
                    if (new Date().before(accountDO.getLockTime())) {
                        //用户被锁定
                        signInDTO.setSuccess(false);
                        signInDTO.setMessage("The account is locked. Please try again later.");
                        signInDTO.setCode(403);
                        return signInDTO;
                    }
                }
                //检查密码是否正确
                if (passwordStorageUtils.verifyPassword(password, accountDO.getPassword())) {
                    //密码正确，判断是否需要 OTP验证
                    if (accountDO.getIsOpenOtp() && !stringUtil.isEmpty(accountDO.getTotp())) {
                        //需要 OTP验证
                        if (stringUtil.isEmpty(signInDTO.getOtp())) {
                            //OTP为空
                            signInDTO.setSuccess(false);
                            signInDTO.setMessage("Require OTP password");
                            signInDTO.setCode(411);
                            return signInDTO;
                        }
                        if (!totpService.authcode(signInDTO.getOtp(), accountDO.getTotp())){
                            //OTP验证失败
                            signInDTO.setSuccess(false);
                            signInDTO.setMessage("OTP Password Validation Failed");
                            signInDTO.setCode(403);
                            return signInDTO;
                        }
                    }
                    //颁发Token
                    Map<String, String> data = new HashMap<>();
                    data.put("Authorization", "Bearer " +
                            issuanceToken(accountDO,
                                    signInDTO.getAudience(),
                                    signInDTO.getRemember() == 1 ? true : false));
                    signInDTO.setSuccess(true);
                    signInDTO.setMessage("Success!");
                    signInDTO.setCode(200);
                    signInDTO.setData(data);
                    return signInDTO;
                } else {
                    //密码错误
                    signInDTO.setSuccess(false);
                    signInDTO.setMessage("ERROR Incorrect account or password");
                    signInDTO.setCode(403);
                    //记录错误次数
                    int tries = accountDO.getTries() + 1;
                    accountDO.setTries(tries < 0 ? 1 : tries);
                    if (accountDO.getTries() >= 5) {
                        //错误次数累计到5次，锁定30分钟
                        Calendar nowTime = Calendar.getInstance();
                        nowTime.add(Calendar.MINUTE, 30);
                        accountDO.setLockTime(nowTime.getTime());
                        signInDTO.setMessage("The account is locked. Please try again later.");
                    }
                    //更新
                    updateAccount(accountDO);
                }
            } else {
                //用户被禁用
                signInDTO.setSuccess(false);
                signInDTO.setMessage("Account are forbidden to Sign In");
                signInDTO.setCode(403);
            }
        } else {
            //用户不存在
            signInDTO.setSuccess(false);
            signInDTO.setMessage("ERROR Incorrect account or password");
            signInDTO.setCode(403);
        }
        return signInDTO;
    }

    public UserDTO findByAccountname(String accountName) {
        AccountDOExample accountDOExample = new AccountDOExample();
        accountDOExample.createCriteria()
                .andAccountEqualTo(accountName);
        return ejbGenerator.convert(accountDOMapper.selectByExample(accountDOExample).get(0), UserDTO.class);
    }

    public int updateAccount(AccountDO accountDO) {
        return accountDOMapper.updateByPrimaryKey(accountDO);
    }

    /**
     * 颁发Token凭证
     *
     * @param accountDO
     * @param isRemember
     * @return
     */
    private String issuanceToken(AccountDO accountDO, String audience, boolean isRemember) {
        TokenSubject tokenSubject = new TokenSubject();
        tokenSubject.setAccount(accountDO.getAccount());
        String token = "";
        try {
            token = jwtService.getToken(tokenSubject, audience);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //将Token写入数据库，登出时需要注销，记住我有效期7天，否则有效期30分钟
        recordToken(accountDO, token, isRemember);
        return token;
    }

    /**
     * 记录Token到数据库持久化
     *
     * @param accountDO
     * @param token
     * @param isRemember
     */
    @Async
    public void recordToken(AccountDO accountDO, String token, boolean isRemember) {
        TokenDO tokenDO = new TokenDO();
        tokenDO.setAccount(accountDO.getAccount());
        tokenDO.setToken(token);
        tokenDO.setIsRemember(isRemember);
        Date expirationTime;
        if (isRemember) {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.DATE, 7);
            expirationTime = nowTime.getTime();
        } else {
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, 30);
            expirationTime = nowTime.getTime();
        }
        tokenDO.setExpirationTime(expirationTime);
        try {
            tokenDOMapper.insertSelective(tokenDO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 检查Token的有效期是否过期
     *
     * @param token
     * @param audience
     * @return
     */
    public boolean checkTokenExtension(String token, String audience) {
        if (jwtService.validateToken(token)) {
            try {
                TokenSubject tokenSubject = jwtService.readToken(token, audience);
                if (tokenSubject != null) {
                    TokenDOExample tokenDOExample = new TokenDOExample();
                    tokenDOExample.createCriteria()
                            .andAccountEqualTo(tokenSubject.getAccount())
                            .andTokenEqualTo(token);
                    List<TokenDO> tokenDOS = tokenDOMapper.selectByExample(tokenDOExample);
                    if (tokenDOS != null && tokenDOS.size() > 0) {
                        TokenDO tokenDO = tokenDOS.get(0);
                        if (new Date().before(tokenDO.getExpirationTime())) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

    /**
     * 延长Token有效期
     *
     * @param token
     * @param audience
     */
    @Async
    public void extensionToken(String token, String audience) {
        if (jwtService.validateToken(token)) {
            try {
                TokenSubject tokenSubject = jwtService.readToken(token, audience);
                if (tokenSubject != null) {
                    if (checkTokenExtension(token, audience)) {
                        TokenDOExample tokenDOExample = new TokenDOExample();
                        tokenDOExample.createCriteria()
                                .andAccountEqualTo(tokenSubject.getAccount())
                                .andTokenEqualTo(token);
                        List<TokenDO> tokenDOS = tokenDOMapper.selectByExample(tokenDOExample);
                        if (tokenDOS != null && tokenDOS.size() > 0) {
                            TokenDO tokenDO = tokenDOS.get(0);
                            if (!tokenDO.getIsRemember()) {
                                //延长至现在时间后30分钟有效期
                                Calendar nowTime = Calendar.getInstance();
                                nowTime.add(Calendar.MINUTE, 30);
                                tokenDO.setExpirationTime(nowTime.getTime());
                                tokenDOMapper.updateByPrimaryKeySelective(tokenDO);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}

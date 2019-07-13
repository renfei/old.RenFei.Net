package net.renfei.dao.entity;

import java.util.Date;

public class AccountDO {
    private Long id;

    private String account;

    private String password;

    private String totp;

    private Boolean isOpenOtp;

    private Integer status;

    private Date registrationTime;

    private Integer tries;

    private Date lockTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTotp() {
        return totp;
    }

    public void setTotp(String totp) {
        this.totp = totp == null ? null : totp.trim();
    }

    public Boolean getIsOpenOtp() {
        return isOpenOtp;
    }

    public void setIsOpenOtp(Boolean isOpenOtp) {
        this.isOpenOtp = isOpenOtp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getTries() {
        return tries;
    }

    public void setTries(Integer tries) {
        this.tries = tries;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }
}
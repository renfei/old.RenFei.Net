package net.renfei.dao.entity;

import java.util.Date;

public class SecretKeyDO {
    private byte[] uid;

    private Date expireTime;

    public byte[] getUid() {
        return uid;
    }

    public void setUid(byte[] uid) {
        this.uid = uid;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
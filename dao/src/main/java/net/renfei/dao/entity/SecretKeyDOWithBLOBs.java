package net.renfei.dao.entity;

public class SecretKeyDOWithBLOBs extends SecretKeyDO {
    private String serverPrivateKey;

    private String clientPublicKey;

    private String aesKey;

    public String getServerPrivateKey() {
        return serverPrivateKey;
    }

    public void setServerPrivateKey(String serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey == null ? null : serverPrivateKey.trim();
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey == null ? null : clientPublicKey.trim();
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey == null ? null : aesKey.trim();
    }
}
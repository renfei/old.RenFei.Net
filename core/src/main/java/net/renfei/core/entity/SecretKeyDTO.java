package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.SecretKeyDOWithBLOBs;

@Data
public class SecretKeyDTO extends SecretKeyDOWithBLOBs {
    private String UUID;

    public String getUUID() {
        return new String(this.getUid());
    }

    public void setUUID(String UUID) {
        this.setUid(UUID.replace("-", "").getBytes());
    }
}

package net.renfei.web.entity;

import lombok.Data;

@Data
public class SignInVO {
    private String account;
    private String password;
    private String otp;
    private int remember;
}

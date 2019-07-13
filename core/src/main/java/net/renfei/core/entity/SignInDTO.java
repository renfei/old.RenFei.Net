package net.renfei.core.entity;

import lombok.Data;

@Data
public class SignInDTO {
    private String uuid;
    private String account;
    private String password;
    private String otp;
    private String audience;
    private int remember;
    /***********************/
    private Boolean success;
    private String message;
    private int code;
    private Object data;
}

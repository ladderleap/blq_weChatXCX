package com.blq.model.domain;

import lombok.Data;

@Data
public class LoginUser {
    private String openid;
    private String session_key;
    private String unionid;
}

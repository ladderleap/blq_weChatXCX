package com.blq.model.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String code;

    private String pingtai;
}

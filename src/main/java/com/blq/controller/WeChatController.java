package com.blq.controller;

import com.blq.common.BaseResponse;
import com.blq.common.ErrorCode;
import com.blq.common.ResultUtils;
import com.blq.model.domain.User;
import com.blq.model.request.LoginResponse;
import com.blq.model.request.UserLoginRequest;
import com.blq.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/user")
public class WeChatController {
    @Autowired
    UserLoginService userLoginService;


    @PostMapping("/wxxcxlogin")
    public BaseResponse<LoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        if(userLoginRequest==null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        LoginResponse loginResponse = userLoginService.userLogin(userLoginRequest);
        return ResultUtils.success(loginResponse);

    }


    @GetMapping("/userinfo")
    public BaseResponse<User> userinfo(){
//        System.out.println(123123);
        User user = new User();
        user.setPhone("112233");

        user.setBalance(new BigDecimal(0));
        user.setBonusBalance(new BigDecimal(0));
        return ResultUtils.success(user);
    }

}

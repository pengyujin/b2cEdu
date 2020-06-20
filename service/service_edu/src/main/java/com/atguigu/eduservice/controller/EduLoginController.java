package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description="登陆管理")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin // 解决跨域
public class EduLoginController {
    
    // login
    @PostMapping("login")
    public R login() {
        return R.ok();
    }
}

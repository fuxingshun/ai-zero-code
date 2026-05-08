package com.fxs.aizerocode.controller;

import com.fxs.aizerocode.common.BaseResponse;
import com.fxs.aizerocode.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public BaseResponse<String> hello(){
        return ResultUtils.success("Hello, World!");
    }

}

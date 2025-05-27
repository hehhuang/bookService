package com.book.controller;

import com.book.config.TokenInterceptor;
import com.book.entity.ResultModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/login")
public class LoginController extends BaseController {
    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "API Success!";

    @GetMapping
    public ResultModel login() {
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, TokenInterceptor.VALID_TOKEN);
    }

}

package com.book.controller;

import com.book.entity.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.NestedServletException;

@ResponseBody
@ControllerAdvice
public class BaseController {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResultModel handleRuntimeException(RuntimeException ex) {
        ResultModel rm = new ResultModel();
        rm.setResultCode(HttpStatus.BAD_REQUEST.value() + "");
        rm.setResultMsg("BAD_REQUEST");
        rm.setData(ex.getMessage());
        return rm;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResultModel handleException(Exception ex) {
        ResultModel rm = new ResultModel();
        rm.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "");
        rm.setResultMsg("INTERNAL_SERVER_ERROR");
        rm.setData(ex.getMessage());
        return rm;
    }

}

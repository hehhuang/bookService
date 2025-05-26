package com.book.controller;

import com.book.entity.BookEntity;
import com.book.entity.ResultModel;
import com.book.facade.BookFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookFacade bookFacade;

    private static final String SUCCESS_CODE = "200";
    private static final String SUCCESS_MESSAGE = "API Success!";

    @GetMapping("/findAllBook")
    public ResultModel findAllBook(){
        List<BookEntity> bookEntityList = bookFacade.findAllBook();
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntityList);
    }
    @GetMapping("/addUser")
    public String addUser(){
        return  "success";
    }
}

package com.book.controller;


import com.book.config.TokenInterceptor;
import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.PageResult;
import com.book.entity.ResultModel;
import com.book.enums.LanguageEnum;
import com.book.facade.BookFacade;
import com.book.service.chain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.book.controller.BookController.ID_SHOULD_NOT_BE_NULL_ERROR_MESSAGE;
import static com.book.controller.BookController.SUCCESS_CODE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    @InjectMocks
    private LoginController bookController = new LoginController();

    @Test
    public void loginTest() throws Exception {
        ResultModel resultModel = bookController.login();
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
        Assertions.assertEquals(TokenInterceptor.VALID_TOKEN, resultModel.getData());
    }

}

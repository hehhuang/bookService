package com.book.controller;


import com.book.config.TokenInterceptor;
import com.book.entity.BookEntity;
import com.book.entity.ResultModel;
import com.book.facade.BookFacade;

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
public class BookControllerTest {
    private BookFacade bookFacade = Mockito.mock(BookFacade.class);

    @InjectMocks
    private BookController bookController = new BookController(bookFacade);

    private List<BookEntity> initBookList() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        bookEntity.setTitle("JAVA入门");
        bookEntity.setAuthor("author1");
        bookEntity.setCategory("计算机");
        bookEntity.setAgeCategory("TEENAGER");
        bookEntity.setPrice(100d);
        bookEntity.setBookCount(10L);
        bookEntityList.add(bookEntity);
        return bookEntityList;
    }

    @Test
    public void loginTest() throws Exception {
        ResultModel resultModel = bookController.login();
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
        Assertions.assertEquals(TokenInterceptor.VALID_TOKEN, resultModel.getData());
    }

    @Test
    public void findAllBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        when(bookFacade.findAllBook()).thenReturn(bookList);
        ResultModel resultModel = bookController.findAllBook();
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
        Assertions.assertEquals(bookList, resultModel.getData());
    }

    @Test
    public void findBookByIdTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        when(bookFacade.findBookById(id)).thenReturn(bookEntity);
        ResultModel resultModel = bookController.findBookById(id);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
        Assertions.assertEquals(bookEntity, resultModel.getData());
    }

    @Test
    public void addBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        ResultModel resultModel = bookController.addBook(bookEntity);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());

    }

    @Test
    public void updateBookTest_NoException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        ResultModel resultModel = bookController.updateBook(bookEntity);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());

    }

    @Test
    public void updateBookTest_WithException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        bookEntity.setId(null);
        Exception exception = assertThrows(Exception.class, () -> {
            bookController.updateBook(bookEntity);
        });
        Assertions.assertEquals(exception.getMessage(), ID_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);

    }

    @Test
    public void deleteBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        ResultModel resultModel = bookController.deleteBook(id);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
    }
}

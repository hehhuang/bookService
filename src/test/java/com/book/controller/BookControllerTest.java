package com.book.controller;


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
public class BookControllerTest {
    private BookFacade bookFacade = Mockito.mock(BookFacade.class);

    @InjectMocks
    private BookController bookController = new BookController(bookFacade);

    private List<BookEntity> initBookList() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        BookEntity teenagerBook = new BookEntity();
        teenagerBook.setId(1);
        teenagerBook.setTitle("JAVA入门");
        teenagerBook.setAuthor("author1");
        teenagerBook.setCategory("计算机");
        teenagerBook.setAgeCategory("TEENAGER");
        teenagerBook.setLanguage("CHINESE");
        teenagerBook.setStatus(1);
        teenagerBook.setPrice(100d);
        teenagerBook.setBookCount(10L);
        bookEntityList.add(teenagerBook);

        BookEntity childrenBook = new BookEntity();
        childrenBook.setId(2);
        childrenBook.setTitle("childrenBook");
        childrenBook.setAuthor("author2");
        childrenBook.setCategory("计算机");
        childrenBook.setAgeCategory("TEENAGER");
        childrenBook.setLanguage("ENGLISH");
        childrenBook.setStatus(1);
        childrenBook.setPrice(100d);
        childrenBook.setBookCount(10L);
        bookEntityList.add(childrenBook);

        BookEntity adultBook = new BookEntity();
        adultBook.setId(2);
        adultBook.setTitle("adultBook");
        adultBook.setAuthor("author2");
        adultBook.setCategory("计算机");
        adultBook.setAgeCategory("TEENAGER");
        adultBook.setLanguage("ENGLISH");
        adultBook.setStatus(1);
        adultBook.setPrice(100d);
        adultBook.setBookCount(10L);
        bookEntityList.add(adultBook);

        return bookEntityList;
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
    public void updateBookTest_WithRuntimeException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        String ERROR = "Server Error";
        when(bookFacade.findBookById(id)).thenThrow(new RuntimeException(ERROR));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ResultModel resultModel = bookController.findBookById(id);
        });
        Assertions.assertEquals(exception.getMessage(), ERROR);

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

    @Test
    public void pageSearchTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        BookQueryVo queryVo = new BookQueryVo();
        queryVo.setPageSize(1);
        queryVo.setPageNum(1);
        PageResult<BookEntity> bookEntityPageResult = new PageResult<>(1, queryVo.getPageNum(), queryVo.getPageSize(), bookList);
        when(bookFacade.getBooksByPage(queryVo)).thenReturn(bookEntityPageResult);
        ResultModel resultModel = bookController.pageSearchBook(queryVo);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
        Assertions.assertEquals(bookEntityPageResult, resultModel.getData());
    }

    @Test
    public void recommendBookByAgeTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        Integer age = 5;
        String language= LanguageEnum.CHINESE.getCode();
        when(bookFacade.recommendBook(age,language)).thenReturn(bookList);
        ResultModel resultModel = bookController.recommendBook(age, language);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());
        Assertions.assertEquals(bookList, resultModel.getData());
    }

    @Test
    public void orderBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        Order order = new Order();
        order.setBookId(id);
        order.setOrderCount(1);
        order.setUserBalance(100d);
        ResultModel resultModel = bookController.orderBook(order);
        Assertions.assertEquals(SUCCESS_CODE, resultModel.getResultCode());

    }
}

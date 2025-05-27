package com.book.controller;


import com.alibaba.fastjson.JSON;
import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.PageResult;
import com.book.enums.LanguageEnum;
import com.book.facade.BookFacade;
import com.book.service.chain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
public class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookFacade bookFacade; // 模拟 Service

    private List<BookEntity> initBookList() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1);
        bookEntity.setTitle("JAVA入门");
        bookEntity.setAuthor("author1");
        bookEntity.setCategory("计算机");
        bookEntity.setAgeCategory("TEENAGER");
        bookEntity.setLanguage("CHINESE");
        bookEntity.setStatus(1);
        bookEntity.setPrice(100d);
        bookEntity.setBookCount(10L);
        bookEntityList.add(bookEntity);
        return bookEntityList;
    }

    @Test
    public void findAllBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        when(bookFacade.findAllBook()).thenReturn(bookList);
        // 测试 HTTP 请求
        mockMvc.perform(get("/v1/books").header("token", "aaa"))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data.size()").value(1));
    }

    @Test
    public void findBookByIdTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        when(bookFacade.findBookById(id)).thenReturn(bookEntity);
        mockMvc.perform(get("/v1/books/"+id).header("token", "aaa"))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data.id").value(id));
    }

    @Test
    public void findBookByIdTest_WithRuntimeException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        String ERROR = "Server Error";
        when(bookFacade.findBookById(id)).thenThrow(new RuntimeException(ERROR));
        mockMvc.perform(get("/v1/books/"+id).header("token", "aaa"))
                .andExpect(status().is(400));
    }

    @Test
    public void addBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        mockMvc.perform(post("/v1/books").header("token", "aaa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(bookEntity)))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)));

    }

    @Test
    public void updateBookTest_NoException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        mockMvc.perform(put("/v1/books").header("token", "aaa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(bookEntity)))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)));

    }

    @Test
    public void updateBookTest_WithException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        bookEntity.setId(null);
        mockMvc.perform(put("/v1/books").header("token", "aaa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(bookEntity)))
                .andExpect(status().is(500));

    }

    @Test
    public void deleteBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        mockMvc.perform(delete("/v1/books/"+id).header("token", "aaa"))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)));
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
        mockMvc.perform(post("/v1/books/page-search").header("token", "aaa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(queryVo)))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data.records[0].id").value(id));
    }

    @Test
    public void recommendBookByAgeTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        Integer age = 5;
        String language= LanguageEnum.CHINESE.getCode();
        when(bookFacade.recommendBook(age,language)).thenReturn(bookList);
        mockMvc.perform(get("/v1/books/recommend/"+age).header("token", "aaa"))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)))
                .andExpect(jsonPath("$.data[0].id").value(id));
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
        mockMvc.perform(post("/v1/books/orders").header("token", "aaa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(order)))
                .andExpect(status().is(Integer.parseInt(BookController.SUCCESS_CODE)));

    }
}

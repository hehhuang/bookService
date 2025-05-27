package com.book.facade;

import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.PageResult;
import com.book.service.chain.Order;

import java.util.List;

public interface BookFacade {
    List<BookEntity> findAllBook();

    BookEntity findBookById(Integer id);

    PageResult<BookEntity> getBooksByPage(BookQueryVo query);

    int addBook(BookEntity bookEntity);

    int updateBook(BookEntity bookEntity) throws Exception;

    int deleteBook(Integer id) throws Exception;

    List<BookEntity> recommendBook(Integer age, String language);

    Double orderBook(Order order);
}

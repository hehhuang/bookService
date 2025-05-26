package com.book.facade;

import com.book.entity.BookEntity;

import java.util.List;

public interface BookFacade {
    List<BookEntity> findAllBook();

    BookEntity findBookById(Integer id);

    int addBook(BookEntity bookEntity);

    int updateBook(BookEntity bookEntity);

    int deleteBook(Integer id);
}

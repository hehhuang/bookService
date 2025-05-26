package com.book.service;

import com.book.entity.BookEntity;
import com.book.facade.BookFacade;
import com.book.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookService")
public class BookService implements BookFacade {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookEntity> findAllBook() {
        return bookMapper.findAllBook();
    }

    @Override
    public BookEntity findBookById(Integer id) {
        return bookMapper.findBookById(id);
    }

    @Override
    public int addBook(BookEntity bookEntity) {
        return bookMapper.addBook(bookEntity);
    }

    @Override
    public int updateBook(BookEntity bookEntity) {
        return bookMapper.updateBook(bookEntity);
    }

    @Override
    public int deleteBook(Integer id) {
        return bookMapper.deleteBook(id);
    }
}

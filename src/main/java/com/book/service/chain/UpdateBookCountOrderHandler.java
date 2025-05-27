package com.book.service.chain;

import com.book.entity.BookEntity;
import com.book.mapper.BookMapper;

import static com.book.service.BookService.getCurrentTime;

public class UpdateBookCountOrderHandler implements OrderHandler {
    private BookMapper bookMapper;
    private BookEntity bookEntity;

    public static final String UPDATE_BOOK_ERROR ="update book issue";

    public UpdateBookCountOrderHandler(BookMapper bookMapper,BookEntity bookEntity) {
        this.bookMapper = bookMapper;
        this.bookEntity = bookEntity;
    }

    @Override
    public void handle(Order order) {
        bookEntity.setBookCount(bookEntity.getBookCount() - order.getOrderCount());
        bookEntity.setUpdateTime(getCurrentTime());
        try {
            bookMapper.updateBook(bookEntity);
        } catch (Exception e) {
            throw new RuntimeException(UPDATE_BOOK_ERROR);
        }
    }
}

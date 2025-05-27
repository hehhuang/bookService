package com.book.service.chain;

import com.book.entity.BookEntity;

public class BookStatusOrderHandler implements OrderHandler {
    private OrderHandler next;

    public static final String BOOK_STATUS_ERROR ="this book is not available now";

    public BookStatusOrderHandler(OrderHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Order order) {
        if (order.getBookStatus() != null && order.getBookStatus() == 1) {
            next.handle(order);
        } else {
            throw new RuntimeException(BOOK_STATUS_ERROR);
        }
    }
}

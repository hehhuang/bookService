package com.book.service.chain;

import com.book.entity.BookEntity;

public class BookStockOrderHandler implements OrderHandler {
    private OrderHandler next;

    public static final String BOOK_STOCK_ERROR ="The book inventory is insufficient.";

    public BookStockOrderHandler(OrderHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Order order) {
        if (order.getStockCount() != null && order.getStockCount() >= order.getOrderCount()) {
            next.handle(order);
        } else {
            throw new RuntimeException(BOOK_STOCK_ERROR);
        }
    }
}

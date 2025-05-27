package com.book.service.chain;

import com.book.entity.BookEntity;

public class AddBookInfoOrderHandler implements OrderHandler {
    private OrderHandler next;
    private BookEntity bookEntity;

    public static final String ORDER_COUNT_ERROR ="orderCount is 0";

    public AddBookInfoOrderHandler(OrderHandler next, BookEntity bookEntity) {
        this.next = next;
        this.bookEntity = bookEntity;
    }

    @Override
    public void handle(Order order) {
        order.setBookStatus(bookEntity.getStatus());
        order.setStockCount(bookEntity.getBookCount());
        order.setBookPrice(bookEntity.getPrice());
        if (order.getOrderCount() != null && order.getOrderCount() > 0) {
            next.handle(order);
        } else {
            throw new RuntimeException(ORDER_COUNT_ERROR);
        }
    }
}

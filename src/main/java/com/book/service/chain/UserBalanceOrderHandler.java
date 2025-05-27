package com.book.service.chain;

import com.book.entity.BookEntity;

import java.math.BigDecimal;

public class UserBalanceOrderHandler implements OrderHandler {
    private OrderHandler next;

    public static final String USER_BALANCE_ERROR ="Insufficient user balance";

    public UserBalanceOrderHandler(OrderHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Order order) {
        Integer orderCount = order.getOrderCount();
        if (orderCount != null) {
            BigDecimal bookPrice = new BigDecimal(order.getBookPrice().toString());
            BigDecimal totalPrice = bookPrice.multiply(new BigDecimal(orderCount.toString()));
            BigDecimal userBalance = new BigDecimal(order.getUserBalance().toString());
            BigDecimal subtractResult = userBalance.subtract(totalPrice);
            double finalUserBalance = subtractResult.doubleValue();
            if (finalUserBalance >= 0) {
                order.setFinalUserBalance(finalUserBalance);
                next.handle(order);
            } else {
                throw new RuntimeException(USER_BALANCE_ERROR);
            }
        }
    }
}

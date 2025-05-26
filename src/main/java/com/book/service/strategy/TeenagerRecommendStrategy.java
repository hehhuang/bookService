package com.book.service.strategy;

import com.book.entity.BookEntity;
import com.book.enums.BookCategory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class TeenagerRecommendStrategy implements AgeBasedRecommendStrategy {
    @Override
    public List<BookEntity> recommendBooks(List<BookEntity> books) {
        if (CollectionUtils.isEmpty(books)) {
            return books;
        }
        return books.stream()
                .filter(book -> BookCategory.TEENAGER.getCode().equals(book.getAgeCategory()))
                .collect(Collectors.toList());
    }
    @Override
    public void doDisCount(List<BookEntity> books) {
        if (CollectionUtils.isEmpty(books)) {
            return;
        }
        books.forEach(book -> {
            Double price = book.getPrice();
            if (price != null) {
                BigDecimal priceDecimal = new BigDecimal(price.toString());
                BigDecimal discount = new BigDecimal("0.6");
                BigDecimal discountedPrice = priceDecimal.multiply(discount)
                        .setScale(2, RoundingMode.HALF_UP); // 保留 2 位小数
                book.setPrice(discountedPrice.doubleValue());
            }
        });
    }
}

package com.book.service.strategy;

import com.book.entity.BookEntity;

import java.util.List;

public interface AgeBasedRecommendStrategy {
    List<BookEntity> recommendBooks(List<BookEntity> books);

    void doDisCount(List<BookEntity> books);
}

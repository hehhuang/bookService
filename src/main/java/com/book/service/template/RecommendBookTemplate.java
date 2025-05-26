package com.book.service.template;

import com.book.entity.BookEntity;
import com.book.service.strategy.AgeBasedRecommendStrategy;
import com.book.service.strategy.RecommendStrategyFactory;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RecommendBookTemplate {
    public final List<BookEntity> recommendBooks(Integer age) {
        // 1. 获取所有书籍
        List<BookEntity> allBooks = fetchAllBooks();

        // 2. 创建年龄筛选策略
        AgeBasedRecommendStrategy strategy = RecommendStrategyFactory.createStrategy(age);

        // 3. 应用筛选策略
        List<BookEntity> recommendBooks = strategy.recommendBooks(allBooks);
        strategy.doDisCount(recommendBooks);

        // 4. 应用额外的子类特定筛选条件
        List<BookEntity> finalBooks = applyAdditionalFilters(recommendBooks);

        // 5. 返回结果
        return finalBooks;
    }

    // 获取所有书籍的抽象方法（由子类实现）
    protected abstract List<BookEntity> fetchAllBooks();

    // 应用额外筛选条件的钩子方法（子类可选实现）: 这里规定只会展示在售书籍
    protected List<BookEntity> applyAdditionalFilters(List<BookEntity> books) {
        return books.stream()
                .filter(book -> book.getStatus() != null && book.getStatus() == 1)
                .collect(Collectors.toList());
    }
}

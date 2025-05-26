package com.book.service.strategy;

public class RecommendStrategyFactory {
    public static AgeBasedRecommendStrategy createStrategy(Integer age) {
        if (age <= 12) {
            return new ChildrenRecommendStrategy();
        } else if (age <= 18) {
            return new TeenagerRecommendStrategy();
        } else {
            return new AdultRecommendStrategy();
        }
    }
}

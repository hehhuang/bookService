package com.book.service.template;

import com.book.enums.LanguageEnum;
import org.springframework.stereotype.Service;

@Service
public class RecommendBookFactory {
    private RecommendChineseBook recommendChineseBook;
    private RecommendEnglishBook recommendEnglishBook;
    public RecommendBookFactory(RecommendChineseBook recommendChineseBook,RecommendEnglishBook recommendEnglishBook) {
        this.recommendChineseBook = recommendChineseBook;
        this.recommendEnglishBook = recommendEnglishBook;
    }

    public RecommendBookTemplate createRecommendBookService(String language) {
        if (LanguageEnum.CHINESE.getCode().equals(language)) {
            return recommendChineseBook;
        } else {
            return recommendEnglishBook;
        }
    }

}

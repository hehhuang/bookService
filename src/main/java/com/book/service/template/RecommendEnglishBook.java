package com.book.service.template;

import com.book.entity.BookEntity;
import com.book.enums.LanguageEnum;
import com.book.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendEnglishBook extends RecommendBookTemplate{
    private BookMapper bookMapper;
    public RecommendEnglishBook(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    protected List<BookEntity> fetchAllBooks() {
        return bookMapper.findBookByLanguage(LanguageEnum.ENGLISH.getCode());
    }
}

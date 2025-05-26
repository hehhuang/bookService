package com.book.mapper;

import com.book.entity.BookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {

    List<BookEntity> findAllBook();

    BookEntity findBookById(Integer id);

    int addBook(BookEntity bookEntity);

    int updateBook(BookEntity bookEntity);

    int deleteBook(Integer id);

}

package com.book.mapper;

import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {

    List<BookEntity> findAllBook();

    BookEntity findBookById(Integer id);

    List<BookEntity> findBookByLanguage(String language);

    int addBook(BookEntity bookEntity);

    int updateBook(BookEntity bookEntity);

    int deleteBook(Integer id);

    // 查询总记录数
    Long countByCondition(BookQueryVo query);

    // 查询分页数据
    List<BookEntity> selectByCondition(BookQueryVo query);

}

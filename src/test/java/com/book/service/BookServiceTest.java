package com.book.service;

import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.PageResult;
import com.book.enums.LanguageEnum;
import com.book.mapper.BookMapper;
import com.book.service.chain.Order;
import com.book.service.template.RecommendBookFactory;
import com.book.service.template.RecommendChineseBook;
import com.book.service.template.RecommendEnglishBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.book.service.BookService.NO_RELATED_BOOK;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private BookMapper bookMapper = Mockito.mock(BookMapper.class);
    private RecommendBookFactory recommendBookFactory = Mockito.mock(RecommendBookFactory.class);

    @InjectMocks
    private BookService bookService = new BookService(bookMapper,recommendBookFactory);

    private List<BookEntity> initBookList() {
        List<BookEntity> bookEntityList = new ArrayList<>();
        BookEntity teenagerBook = new BookEntity();
        teenagerBook.setId(1);
        teenagerBook.setTitle("JAVA入门");
        teenagerBook.setAuthor("author1");
        teenagerBook.setCategory("计算机");
        teenagerBook.setAgeCategory("TEENAGER");
        teenagerBook.setLanguage("CHINESE");
        teenagerBook.setStatus(1);
        teenagerBook.setPrice(100d);
        teenagerBook.setBookCount(10L);
        bookEntityList.add(teenagerBook);

        BookEntity childrenBook = new BookEntity();
        childrenBook.setId(2);
        childrenBook.setTitle("childrenBook");
        childrenBook.setAuthor("author2");
        childrenBook.setCategory("计算机");
        childrenBook.setAgeCategory("TEENAGER");
        childrenBook.setLanguage("ENGLISH");
        childrenBook.setStatus(1);
        childrenBook.setPrice(100d);
        childrenBook.setBookCount(10L);
        bookEntityList.add(childrenBook);

        BookEntity adultBook = new BookEntity();
        adultBook.setId(2);
        adultBook.setTitle("adultBook");
        adultBook.setAuthor("author2");
        adultBook.setCategory("计算机");
        adultBook.setAgeCategory("TEENAGER");
        adultBook.setLanguage("ENGLISH");
        adultBook.setStatus(1);
        adultBook.setPrice(100d);
        adultBook.setBookCount(10L);
        bookEntityList.add(adultBook);

        return bookEntityList;
    }


    @Test
    public void findAllBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        when(bookMapper.findAllBook()).thenReturn(bookList);
        List<BookEntity> result = bookService.findAllBook();
        Assertions.assertEquals(result, bookList);
    }

    @Test
    public void findBookByIdTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        when(bookMapper.findBookById(id)).thenReturn(bookEntity);
        BookEntity result = bookService.findBookById(id);
        Assertions.assertEquals(bookEntity, result);
    }

    @Test
    public void updateBookTest_WithRuntimeException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        String ERROR = "Server Error";
        when(bookMapper.findBookById(id)).thenThrow(new RuntimeException(ERROR));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookService.findBookById(id);
        });
        Assertions.assertEquals(exception.getMessage(), ERROR);

    }

    @Test
    public void addBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        bookService.addBook(bookEntity);
        verify(bookMapper,times(1)).addBook(bookEntity);
    }

    @Test
    public void updateBookTest_NoException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        when(bookMapper.findBookById(id)).thenReturn(bookEntity);
        bookService.updateBook(bookEntity);
        verify(bookMapper,times(1)).updateBook(bookEntity);
    }

    @Test
    public void updateBookTest_WithException() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        bookEntity.setId(null);
        Exception exception = assertThrows(Exception.class, () -> {
            bookService.updateBook(bookEntity);
        });
        Assertions.assertEquals(exception.getMessage(), NO_RELATED_BOOK);

    }

    @Test
    public void deleteBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        when(bookMapper.findBookById(id)).thenReturn(bookEntity);
        bookService.deleteBook(id);
        verify(bookMapper,times(1)).deleteBook(id);
    }

    @Test
    public void pageSearchTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        Long total = 1L;
        BookQueryVo queryVo = new BookQueryVo();
        queryVo.setPageSize(1);
        queryVo.setPageNum(1);
        PageResult<BookEntity> bookEntityPageResult = new PageResult<>(total, queryVo.getPageNum(), queryVo.getPageSize(), bookList);
        when(bookMapper.countByCondition(queryVo)).thenReturn(total);
        when(bookMapper.selectByCondition(queryVo)).thenReturn(bookList);
        PageResult<BookEntity> pageResult = bookService.getBooksByPage(queryVo);
        Assertions.assertEquals(pageResult, bookEntityPageResult);
    }

    private List<BookEntity> testRecommendBookByAgeAndLanguage(Integer age, String language) {
        List<BookEntity> bookList = initBookList();
        List<BookEntity> languageFilterResult = bookList.stream().filter(entity -> language.equals(entity.getLanguage())).collect(Collectors.toList());
        if(LanguageEnum.CHINESE.getCode().equals(language)){
            when(recommendBookFactory.createRecommendBookService(language)).thenReturn(new RecommendChineseBook(bookMapper));
        }else {
            when(recommendBookFactory.createRecommendBookService(language)).thenReturn(new RecommendEnglishBook(bookMapper));
        }
        when(bookMapper.findBookByLanguage(language)).thenReturn(languageFilterResult);
        return bookService.recommendBook(age, language);
    }

    @Test
    public void recommendBookByAgeTest_CASE_CHINESE_CHILD() throws Exception {
        Integer age = 5;
        String language= LanguageEnum.CHINESE.getCode();
        List<BookEntity> result = testRecommendBookByAgeAndLanguage(age, language);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void recommendBookByAgeTest_CASE_CHINESE_TEENAGER() throws Exception {
        Integer age = 15;
        String language= LanguageEnum.CHINESE.getCode();
        List<BookEntity> result = testRecommendBookByAgeAndLanguage(age, language);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void recommendBookByAgeTest_CASE_CHINESE_ADULT() throws Exception {
        Integer age = 25;
        String language= LanguageEnum.CHINESE.getCode();
        List<BookEntity> result = testRecommendBookByAgeAndLanguage(age, language);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void recommendBookByAgeTest_CASE_ENGLISH_CHILD() throws Exception {
        Integer age = 5;
        String language= LanguageEnum.ENGLISH.getCode();
        List<BookEntity> result = testRecommendBookByAgeAndLanguage(age, language);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void recommendBookByAgeTest_CASE_ENGLISH_TEENAGER() throws Exception {
        Integer age = 15;
        String language= LanguageEnum.ENGLISH.getCode();
        List<BookEntity> result = testRecommendBookByAgeAndLanguage(age, language);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void recommendBookByAgeTest_CASE_ENGLISH_ADULT() throws Exception {
        Integer age = 25;
        String language= LanguageEnum.ENGLISH.getCode();
        List<BookEntity> result = testRecommendBookByAgeAndLanguage(age, language);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void orderBookTest() throws Exception {
        List<BookEntity> bookList = initBookList();
        BookEntity bookEntity = bookList.get(0);
        Integer id = bookEntity.getId();
        Order order = new Order();
        order.setBookId(id);
        order.setOrderCount(1);
        order.setUserBalance(100d);
        when(bookMapper.findBookById(id)).thenReturn(bookEntity);
        bookService.orderBook(order);

    }
}

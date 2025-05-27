package com.book.service;

import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.PageResult;
import com.book.facade.BookFacade;
import com.book.mapper.BookMapper;
import com.book.service.chain.*;
import com.book.service.template.RecommendBookFactory;
import com.book.service.template.RecommendBookTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("bookService")
public class BookService implements BookFacade {
    private BookMapper bookMapper;
    private RecommendBookFactory recommendBookFactory;

    public BookService(BookMapper bookMapper, RecommendBookFactory recommendBookFactory) {
        this.bookMapper = bookMapper;
        this.recommendBookFactory = recommendBookFactory;
    }

    public static final String NO_RELATED_BOOK = "No related book";

    @Override
    public List<BookEntity> findAllBook() {
        return bookMapper.findAllBook();
    }

    @Override
    public BookEntity findBookById(Integer id) {
        return bookMapper.findBookById(id);
    }

    @Override
    public PageResult<BookEntity> getBooksByPage(BookQueryVo query) {
        // 计算偏移量
        int offset = (query.getPageNum() - 1) * query.getPageSize();
        query.setOffset(offset);

        // 查询总记录数
        Long total = bookMapper.countByCondition(query);

        // 查询分页数据
        List<BookEntity> records = bookMapper.selectByCondition(query);

        // 封装分页结果
        return new PageResult<>(total, query.getPageNum(), query.getPageSize(), records);
    }

    @Override
    public int addBook(BookEntity bookEntity) {
        // set createTime
        bookEntity.setCreateTime(getCurrentTime());
        bookEntity.setActive(true);
        return bookMapper.addBook(bookEntity);
    }

    @Override
    public int updateBook(BookEntity bookEntity) throws Exception {
        checkBookInDb(bookEntity.getId());
        bookEntity.setUpdateTime(getCurrentTime());
        return bookMapper.updateBook(bookEntity);
    }

    @Override
    public int deleteBook(Integer id) throws Exception {
        checkBookInDb(id);
        return bookMapper.deleteBook(id);
    }

    private void checkBookInDb(Integer id) throws Exception {
        BookEntity bookInDb = bookMapper.findBookById(id);
        if (bookInDb == null) {
            throw new Exception(NO_RELATED_BOOK);
        }
    }

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = now.format(formatter);
        return createTime;
    }

    @Override
    public List<BookEntity> recommendBook(Integer age, String language) {
        RecommendBookTemplate recommendBookService = recommendBookFactory.createRecommendBookService(language);
        return recommendBookService.recommendBooks(age);
    }

    @Transactional
    @Override
    public Double orderBook(Order order) {
        BookEntity bookEntity = findBookById(order.getBookId());
        UpdateBookCountOrderHandler updateBookCountOrderHandler = new UpdateBookCountOrderHandler(bookMapper, bookEntity);
        // we can add user table here
        UserBalanceOrderHandler userBalanceOrderHandler = new UserBalanceOrderHandler(updateBookCountOrderHandler);

        BookStockOrderHandler bookStockOrderHandler = new BookStockOrderHandler(userBalanceOrderHandler);
        BookStatusOrderHandler bookStatusOrderHandler = new BookStatusOrderHandler(bookStockOrderHandler);
        AddBookInfoOrderHandler addBookInfoOrderHandler = new AddBookInfoOrderHandler(bookStatusOrderHandler, bookEntity);
        addBookInfoOrderHandler.handle(order);
        return order.getFinalUserBalance();
    }
}

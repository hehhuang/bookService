package com.book.controller;

import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.ResultModel;
import com.book.facade.BookFacade;
import com.book.service.chain.Order;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/books")
@Validated
public class BookController extends BaseController{
    private final BookFacade bookFacade;
    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "API Success!";
    public static final String ID_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "id should not be null";

    @GetMapping
    public ResultModel findAllBook(){
        List<BookEntity> bookEntityList = bookFacade.findAllBook();
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntityList);
    }

    @GetMapping("/{id}")
    public ResultModel findBookById(@PathVariable Integer id) {
        BookEntity bookEntity = bookFacade.findBookById(id);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntity);
    }

    @PostMapping("/page-search")
    public ResultModel pageSearchBook(@RequestBody BookQueryVo query) {
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookFacade.getBooksByPage(query));
    }


    @PostMapping
    public ResultModel addBook(@Valid @RequestBody BookEntity bookEntity){
        bookFacade.addBook(bookEntity);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    @PutMapping
    public ResultModel updateBook(@Valid @RequestBody BookEntity bookEntity) throws Exception {
        if (bookEntity.getId() == null) {
            throw new Exception(ID_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        bookFacade.updateBook(bookEntity);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    @DeleteMapping("/{id}")
    public ResultModel deleteBook(@PathVariable Integer id) throws Exception {
        bookFacade.deleteBook(id);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    /**、
     * 简单工厂+模板方法+策略模式 实现图书按年龄段分类以及有自己对应的折扣，按语言过滤，以及按状态展示
     * @param age
     * @param language
     * @return
     */
    @GetMapping("/recommend/{age}")
    public ResultModel recommendBook(@PathVariable Integer age, @RequestParam(name = "language", defaultValue = "CHINESE") String language) {
        List<BookEntity> bookEntityList = bookFacade.recommendBook(age, language);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntityList);
    }

    /**
     * 责任链模式，确保订单流程正确，包括库存检查，余额检查，book状态检查，最后进行book库存更新，user余额展示
     * @param order
     * @return
     */
    @PostMapping("/orders")
    public ResultModel orderBook(@Valid @RequestBody Order order){
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookFacade.orderBook(order));
    }


}

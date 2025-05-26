package com.book.controller;

import com.book.config.TokenInterceptor;
import com.book.entity.BookEntity;
import com.book.entity.BookQueryVo;
import com.book.entity.ResultModel;
import com.book.enums.LanguageEnum;
import com.book.facade.BookFacade;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class BookController {
    private final BookFacade bookFacade;
    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "API Success!";
    public static final String ID_SHOULD_NOT_BE_NULL_ERROR_MESSAGE = "id should not be null";

    @GetMapping("/login")
    public ResultModel login() {
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, TokenInterceptor.VALID_TOKEN);
    }

    @GetMapping("/findAllBook")
    public ResultModel findAllBook(){
        List<BookEntity> bookEntityList = bookFacade.findAllBook();
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntityList);
    }

    @GetMapping("/findBookById/{id}")
    public ResultModel findBookById(@PathVariable Integer id) {
        BookEntity bookEntity = bookFacade.findBookById(id);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntity);
    }

    @PostMapping("/pageSearchBook")
    public ResultModel pageSearchBook(@RequestBody BookQueryVo query) {
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookFacade.getBooksByPage(query));
    }


    @PostMapping("/addBook")
    public ResultModel addBook(@Valid @RequestBody BookEntity bookEntity){
        bookFacade.addBook(bookEntity);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    @PutMapping("/updateBook")
    public ResultModel updateBook(@Valid @RequestBody BookEntity bookEntity) throws Exception {
        if (bookEntity.getId() == null) {
            throw new Exception(ID_SHOULD_NOT_BE_NULL_ERROR_MESSAGE);
        }
        bookFacade.updateBook(bookEntity);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResultModel deleteBook(@PathVariable Integer id) throws Exception {
        bookFacade.deleteBook(id);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    @GetMapping("/recommendBookByAge/{age}")
    public ResultModel recommendBookByAge(@PathVariable Integer age, @RequestParam(name = "language", defaultValue = "CHINESE") String language) {
        List<BookEntity> bookEntityList = bookFacade.recommendBook(age, language);
        return new ResultModel(SUCCESS_CODE, SUCCESS_MESSAGE, bookEntityList);
    }
}

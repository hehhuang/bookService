package com.book.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class BookEntity {
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String category;

    @NotBlank
    private String ageCategory;

    @NotBlank
    private String language;

    @NotNull
    private Double price;
    private String publishDate;

    //状态：1-在售，2-下架，3-缺货
    @NotNull(message = "状态不能为空")
    private Integer status;

    private String createTime;
    private String updateTime;


    private Long bookCount;
    private Boolean active;



}

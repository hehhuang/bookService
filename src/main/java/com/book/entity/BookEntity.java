package com.book.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookEntity {
    private Integer id;
    private String title;
    private String author;
    private String category;
    private String ageCategory;
    private Double price;
    private String publishDate;
    private String status;

    private String createTime;
    private String updateTime;

    private Long bookCount;
    private Boolean active;



}

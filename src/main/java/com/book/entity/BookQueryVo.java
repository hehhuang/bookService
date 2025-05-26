package com.book.entity;

import lombok.Data;

import java.util.List;

@Data
public class BookQueryVo {
    private List<FilterVo> filterVoList;
    private String sortField = "id";
    private String sortOrder = "desc";
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    private Integer offset;
}


package com.book.entity;

import lombok.Data;

import java.util.List;

@Data
public class FilterVo {
    private String column;
    private List<String> values;
}


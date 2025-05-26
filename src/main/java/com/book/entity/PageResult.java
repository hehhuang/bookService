package com.book.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private long total;        // 总记录数
    private int pageNum;       // 当前页码
    private int pageSize;      // 每页数量
    private int totalPages;    // 总页数
    private List<T> records;   // 数据列表

    public PageResult(long total, int pageNum, int pageSize, List<T> records) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.records = records;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}

package com.book.service.chain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Order {
    @NotNull
    private Integer bookId;
    @NotNull
    private Integer orderCount;
    private Double bookPrice;
    private Long stockCount;
    private Integer bookStatus;
    @NotNull
    private Double userBalance;

    private Double finalUserBalance;

}

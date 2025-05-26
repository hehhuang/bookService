package com.book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel {
    private String resultCode;
    private String resultMsg;
    private Object data;
}

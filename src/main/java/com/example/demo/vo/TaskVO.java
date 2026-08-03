package com.example.demo.vo;

import lombok.Data;

//打包返回前端叫vo
@Data
public class TaskVO {
    private Long id;
    private String title;
    private Boolean finish;
}
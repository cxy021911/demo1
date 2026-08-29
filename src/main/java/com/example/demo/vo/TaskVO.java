package com.example.demo.vo;

import lombok.Data;

import java.time.LocalDate;

//打包返回前端叫vo
@Data

public class TaskVO {
    private Long id;
    //前端传taskTitle，VO就写taskTitle，不用title
    private String taskTitle;
    private Boolean finish;
    private LocalDate taskDate;
}

package com.example.demo.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("daily_task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskTitle;
    private Integer isFinish;
    private LocalDate taskDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
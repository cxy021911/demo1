package com.example.demo.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("plan_task")
public class Plan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskTitle;
    private LocalDate taskDate;
    private LocalDateTime createTime;

}
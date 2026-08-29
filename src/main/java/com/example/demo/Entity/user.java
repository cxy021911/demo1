package com.example.demo.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("user")
public class user {
    private String id;
    private String name;
}
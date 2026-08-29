package com.example.demo.controller;

import com.example.demo.Entity.user;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
 
    // 1. 新增
    @PostMapping("/add")
    public String add(@RequestBody user user) {
        userService.save(user);
        return "新增成功";
    }

    // 2. 根据id删除
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.removeById(id);
        return "删除成功";
    }

    // 3. 修改
    @PutMapping("/update")
    public String update(@RequestBody user user) {
        userService.updateById(user);
        return "修改成功";
    }

    // 4. 根据id查询单个
    @GetMapping("/get/{id}")
    public user getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // 5. 查询全部
    @GetMapping("/list")
    public List<user> list() {
        return userService.list();
    }
}
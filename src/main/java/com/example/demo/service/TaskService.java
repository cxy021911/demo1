package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.Entity.Task;
import com.example.demo.vo.TaskVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TaskService extends IService<Task> {
    List<Map<String, Object>> getTodayStat(LocalDate today);
    List<TaskVO> getTodayTask(LocalDate today);
    void addTask(TaskVO vo);
    void updateTaskStatus(TaskVO vo);
}
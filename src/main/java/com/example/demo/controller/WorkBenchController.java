package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.TaskService;
import com.example.demo.vo.TaskVO;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.example.demo.serviceimpl.TaskServiceImpl;

@RestController
@RequestMapping("/api/work")
@CrossOrigin("*")
public class WorkBenchController {

    @Resource
    private TaskService taskService;

    // 前端：getWorkStat
    @GetMapping("/stat")
    public Result<List<Map<String, Object>>> getWorkStat() {
        return Result.success(taskService.getTodayStat(LocalDate.now()));
    }

    // 前端：getTodoList
    @GetMapping("/task/list")
    public Result<List<TaskVO>> getTodoList() {
        return Result.success(taskService.getTodayTask(LocalDate.now()));
    }

    // 新增任务
    @PostMapping("/task/add")
    public Result<String> addTask(@RequestBody TaskVO vo) {
        // 参数校验
        if(ObjectUtils.isEmpty(vo) || ObjectUtils.isEmpty(vo.getTaskTitle())){
            return Result.fail("任务标题不能为空");
        }
        //不传日期默认今天
        if(vo.getTaskDate() == null){
            vo.setTaskDate(LocalDate.now());
        }
        try {
            taskService.addTask(vo);
            return Result.success("添加成功");
        }catch (RuntimeException e){
            // 获取锁失败 / 重复提交异常返回前端
            return Result.fail(e.getMessage());
        }
    }

    // 修改完成状态
    @PutMapping("/task/status")
    public Result<String> changeStatus(@RequestBody TaskVO vo) {
        taskService.updateTaskStatus(vo);
        return Result.success("状态更新成功");
    }

    // 删除任务
    @DeleteMapping("/task/{id}")
    public Result<String> deleteTask(@PathVariable Long id) {
       taskService.removeById(id);
        return Result.success("删除成功");
    }

    // 前端：getServerStatus
    @GetMapping("/test/msg")
    public Result<Map<String, Object>> getServerStatus() {
        Map<String, Object> serverData = Map.of("code",200,"msg","服务运行正常");
        return Result.success(serverData);
    }
}
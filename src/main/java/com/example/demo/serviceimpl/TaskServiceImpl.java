package com.example.demo.serviceimpl;
import com.example.demo.Entity.Task;
import com.example.demo.service.TaskService;
import com.example.demo.mapper.TaskMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.service.TaskService;
import com.example.demo.vo.TaskVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public List<Map<String, Object>> getTodayStat(LocalDate today) {
        return taskMapper.selectDayStat(today);
    }

    @Override
    public List<TaskVO> getTodayTask(LocalDate today) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getTaskDate, today);
        List<Task> taskList = list(wrapper);
        // 数据库 0/1 转前端 false/true
        return taskList.stream().map(entity -> {
            TaskVO vo = new TaskVO();
            vo.setId(entity.getId());
            vo.setTitle(entity.getTaskTitle());
            vo.setFinish(entity.getIsFinish() == 1);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void addTask(TaskVO vo) {
        Task task = new Task();
        task.setTaskTitle(vo.getTitle());
        task.setIsFinish(0);
        task.setTaskDate(LocalDate.now());
        save(task);
    }

    @Override
    public void updateTaskStatus(TaskVO vo) {
        Task updateTask = new Task();
        updateTask.setId(vo.getId());
        updateTask.setIsFinish(vo.getFinish() ? 1 : 0);
        updateById(updateTask);
    }
}
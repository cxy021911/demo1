package com.example.demo.serviceimpl;

import com.example.demo.Entity.Task;
import com.example.demo.service.TaskService;
import com.example.demo.mapper.TaskMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.vo.TaskVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    //注入redis模板
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 组装分布式锁key：任务标题 + 任务日期
     */
    private String getLockKey(String title, LocalDate taskDate){
        return "task:work:add:lock:" + title + ":" + taskDate;
    }

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
            vo.setTaskTitle(entity.getTaskTitle());
            vo.setFinish(entity.getIsFinish() == 1);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void addTask(TaskVO vo) {
        String lockKey = getLockKey(vo.getTaskTitle(), vo.getTaskDate());
        //抢占锁，5秒自动过期，防止宕机死锁
        Boolean lockSuccess = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "locked", Duration.ofSeconds(5));

        if(Boolean.FALSE.equals(lockSuccess)){
            throw new RuntimeException("请求处理中，请不要重复点击提交");
        }

        try {
            //锁内二次数据库校验，防止并发重复插入
            LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Task::getTaskTitle,vo.getTaskTitle())
                    .eq(Task::getTaskDate,vo.getTaskDate());
            long count = this.count(wrapper);
            if(count > 0){
                throw new RuntimeException("该日期下该任务已存在，请勿重复添加");
            }

            //你原来的保存逻辑完全不变
            Task task = new Task();
            task.setTaskTitle(vo.getTaskTitle());
            task.setIsFinish(0);
            task.setTaskDate(vo.getTaskDate());
            task.setCreateTime(LocalDateTime.now());
            save(task);

        }finally {
            //无论成功/异常，一定释放锁
            redisTemplate.delete(lockKey);
        }
    }

    @Override
    public void updateTaskStatus(TaskVO vo) {
        Task updateTask = new Task();
        updateTask.setId(vo.getId());
        updateTask.setIsFinish(vo.getFinish() ? 1 : 0);
        updateById(updateTask);
    }
}

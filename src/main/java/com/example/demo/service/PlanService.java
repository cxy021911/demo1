package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.Entity.Plan;

public interface PlanService extends IService<Plan> {
    /**
     * Redis分布式锁新增任务，高并发防重复插入
     * @param plan 任务实体
     * @return 是否保存成功
     */

}

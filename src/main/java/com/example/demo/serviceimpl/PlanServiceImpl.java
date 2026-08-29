package com.example.demo.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.Entity.Plan;
import com.example.demo.mapper.PlanMapper;
import com.example.demo.service.PlanService;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {
    /**
     * Redis分布式锁新增任务，高并发防重复插入
     * @param plan 任务实体
     * @return 是否保存成功
     */


}

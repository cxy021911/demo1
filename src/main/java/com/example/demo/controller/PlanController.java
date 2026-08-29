package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.Entity.Plan;
import com.example.demo.common.Result;
import com.example.demo.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    /**
     * 新增计划 POST /api/plan/add
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Plan plan){
        if (ObjectUtils.isEmpty(plan)) {
            return Result.fail("请求参数不能为空");
        }
        boolean save = planService.save(plan);
        return Result.success(save);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/{id}")
    public Result<Plan> getById(@PathVariable Long id){
        if(id == null){
            return Result.fail("id不能为空");
        }
        Plan plan = planService.getById(id);
        return Result.success(plan);
    }

    /**
     * 根据日期查询列表
     */
    @GetMapping("/list")
    public Result<List<Plan>> list(@RequestParam LocalDate planDate){
        if(planDate == null){
            return Result.fail("planDate日期参数不能为空");
        }
        LambdaQueryWrapper<Plan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Plan::getTaskDate,planDate);
        List<Plan> list = planService.list(wrapper);
        return Result.success(list);
    }

    /**
     * 统计接口（实体无finish字段，暂时只统计总数量，完成/未完成置0，后续实体加字段再恢复逻辑）
     */
    @GetMapping("/stat")
    public Result<List<Map<String,Object>>> stat(@RequestParam LocalDate planDate){
        if(planDate == null){
            return Result.fail("planDate日期参数不能为空");
        }
        LambdaQueryWrapper<Plan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Plan::getTaskDate,planDate);
        List<Plan> allList = planService.list(wrapper);

        long total = allList.size();
        // 实体没有finish字段，先写死为0，后续实体加上finish再打开stream统计代码
        long finishCount = 0;
        long unFinishCount = total - finishCount;
        double rate = total == 0 ? 0 : Math.round( (double)finishCount / total *100 );

        Map<String,Object> map = new HashMap<>();
        map.put("key","total");
        map.put("label","全部");
        map.put("value",total);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("key","unfinish");
        map2.put("label","未完成");
        map2.put("value",unFinishCount);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("key","finish");
        map3.put("label","已完成");
        map3.put("value",finishCount);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("key","rate");
        map4.put("label","完成率");
        map4.put("value",rate);

        List<Map<String,Object>> statData = List.of(map,map2,map3,map4);
        return Result.success(statData);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody Plan plan){
        if(ObjectUtils.isEmpty(plan) || plan.getId() == null){
            return Result.fail("参数或id不能为空");
        }
        boolean b = planService.updateById(plan);
        return Result.success(b);
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id){
        if(id == null){
            return Result.fail("id不能为空");
        }
        boolean b = planService.removeById(id);
        return Result.success(b);
    }
}

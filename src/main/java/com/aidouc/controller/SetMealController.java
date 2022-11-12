package com.aidouc.controller;

import com.aidouc.common.Result;
import com.aidouc.domain.Category;
import com.aidouc.domain.Setmeal;
import com.aidouc.dto.SetmealDto;
import com.aidouc.service.Impl.CategoryService;
import com.aidouc.service.Impl.SetMealService;
import com.aidouc.service.Impl.SetmealDishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> saveSeaml(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());
        return setMealService.saves(setmealDto);
    }

    @GetMapping("/page")
    public Result<Page> getSetmealList(int page, int pageSize, String name) {
        Page<Setmeal> pagetion = new Page<>(page, pageSize);
        Page<SetmealDto> pagetions = new Page<>();
        LambdaQueryWrapper<Setmeal> lw = new LambdaQueryWrapper<>();
        lw.like(name != null, Setmeal::getName, name);
        lw.orderBy(true, false, Setmeal::getUpdateTime);
        setMealService.page(pagetion, lw);
        BeanUtils.copyProperties(pagetion, pagetions, "records");
        List<Setmeal> list = pagetion.getRecords();
        List<SetmealDto> newList = list.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            Category category = categoryService.getById(item.getCategoryId());
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());
        pagetions.setRecords(newList);
        return Result.success(pagetions);
    }

    @DeleteMapping
    public Result<String> removeBatchs(Long[] ids) {
        log.info("开始删除");
        return setMealService.deleteWidthIds(ids);
    }

    @GetMapping("/list")
    public Result<List<Setmeal>> getSetmeal(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lwt = new LambdaQueryWrapper<>();
        lwt.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lwt.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> list = setMealService.list(lwt);
        return Result.success(list);
    }
}


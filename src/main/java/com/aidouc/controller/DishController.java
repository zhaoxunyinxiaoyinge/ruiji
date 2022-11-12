package com.aidouc.controller;

import com.aidouc.common.Result;
import com.aidouc.domain.Category;
import com.aidouc.domain.Dish;
import com.aidouc.domain.DishFlavor;
import com.aidouc.dto.DishDto;
import com.aidouc.service.Impl.CategoryService;
import com.aidouc.service.Impl.DishFalvorService;
import com.aidouc.service.Impl.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFalvorService dishFalvorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto) {
        dishService.save(dishDto);
        return Result.success("添加成功");
    }

    /**
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page> getList(String name, int page, int pageSize) {
        IPage pagetion = new Page(page, pageSize);
        LambdaQueryWrapper<Dish> lw = new LambdaQueryWrapper();
        lw.like(name != null, Dish::getName, name);
        lw.orderBy(true, false, Dish::getUpdateTime);
        IPage list = dishService.page(pagetion, lw);

        Page<DishDto> doList = new Page();
        BeanUtils.copyProperties(list, doList, "records");
        List<Dish> pageinfo = list.getRecords();
        List<DishDto> newList = pageinfo.stream().map(item -> {
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(item, dto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dto.setCategoryName(category.getName());
            return dto;
        }).collect(Collectors.toList());
        doList.setRecords(newList);
        return Result.success(doList);
    }

    @GetMapping("/{id}")
    public Result<DishDto> getDetil(@PathVariable Long id) {
        return dishService.getDetails(id);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {
        return dishService.updates(dishDto);
    }

    @PostMapping("/status/{val}")
    public Result<String> updateStatus(@PathVariable int val, String ids) {
        String[] arr = ids.split(",");
        for (int i = 0; i < arr.length; i++) {
            Dish dish = new Dish();
            dish.setStatus(val);
            LambdaQueryWrapper<Dish> lw = new LambdaQueryWrapper<>();
            lw.eq(Dish::getId, Long.parseLong(arr[i]));
            dishService.update(dish, lw);
        }
        return Result.success("修改状态成功");
    }

    @DeleteMapping
    public Result<String> removes(String ids) {
        String[] arr = ids.split(",");
        Long[] newArr = new Long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = Long.parseLong(arr[i]);
        }
        dishService.removeById(newArr);
        LambdaQueryWrapper<DishFlavor> lw = new LambdaQueryWrapper<>();
        for (int i = 0; i < newArr[i]; i++) {
            lw.eq(DishFlavor::getDishId, newArr[i]);
            dishFalvorService.remove(lw);
        }
        return Result.success("移除成功");
    }

    @GetMapping("/list")
    public Result<List<DishDto>> getCategoryList(Dish dish) {
        LambdaQueryWrapper<Dish> lw = new LambdaQueryWrapper<>();
        lw.eq(Dish::getCategoryId, dish.getCategoryId());
        lw.orderBy(true, true, Dish::getUpdateTime);
        List<Dish> list = dishService.list(lw);
        List<DishDto> listdto = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            //通过dishid查询口味表；
            LambdaQueryWrapper<DishFlavor> la = new LambdaQueryWrapper<>();
            la.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> favList = dishFalvorService.list(la);
            dishDto.setFlavors(favList);
            return dishDto;
        }).collect(Collectors.toList());

        return Result.success(listdto);
    }
}

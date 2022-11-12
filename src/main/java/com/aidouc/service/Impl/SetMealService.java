package com.aidouc.service.Impl;
import com.aidouc.common.CustomExption;
import com.aidouc.common.Result;
import com.aidouc.dao.SetMealDao;
import com.aidouc.domain.Setmeal;
import com.aidouc.domain.SetmealDish;
import com.aidouc.dto.SetmealDto;
import com.aidouc.service.SetMealInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealService extends ServiceImpl<SetMealDao, Setmeal> implements SetMealInterface {
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * @param setmealDto
     * @return
     */
    @Override
    public Result<String> saves(SetmealDto setmealDto) {
        if(setmealDto==null){
            throw new CustomExption("添加数据异常");
        }
        //保存数据到setmeal表中
        this.save(setmealDto);
        //将数据写入setmeal_dish表中
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

        return Result.success("新增套餐成功");
    }

    /**
     * @param ids
     * @return
     */
    @Override
    public Result<String> deleteWidthIds(Long[] ids) {
        // 查询当前删除的数据是否在可用的状态中
        LambdaQueryWrapper<Setmeal> lw = new LambdaQueryWrapper();
        lw.in(ids!=null,Setmeal::getId, ids);
        int count = this.count(lw);
        if (count > 0) {
            throw new CustomExption("在售套餐不能被删除");
        }
        // 删除当前的套餐
        this.removeByIds(Arrays.asList(ids));
        // 删除当前套餐下的关联setmealdish
        LambdaQueryWrapper<SetmealDish> ld = new LambdaQueryWrapper<>();
        ld.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(ld);
        return Result.success("选择套餐删除成功");
    }
}


package com.aidouc.service.Impl;

import com.aidouc.common.Result;
import com.aidouc.dao.DishDao;
import com.aidouc.domain.Dish;
import com.aidouc.domain.DishFlavor;
import com.aidouc.dto.DishDto;
import com.aidouc.service.DishInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishService extends ServiceImpl<DishDao, Dish> implements DishInterface {
    @Autowired
    private DishFalvorService dishFalvorService;

    //@Transactional
    @Override
    public void saveDish(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> list = dishDto.getFlavors();
        list = list.stream().map(item -> {
            item.setId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFalvorService.saveBatch(list);
    }

    @Override
    public Result<DishDto> getDetails(Long id) {
        // 查询dish表的商品信息
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        //创建lamb表达式
        LambdaQueryWrapper<DishFlavor> lw = new LambdaQueryWrapper<>();
        lw.eq(DishFlavor::getDishId, id);
        //查询dishFalvors对象
        List<DishFlavor> favorList = dishFalvorService.list(lw);
        //动态设置dsisDto 对象
        dishDto.setFlavors(favorList);
        return Result.success(dishDto);
    }

    @Override
    public Result<String> updates(DishDto dishDto) {
        //设置dish 表中的数据操作对象
        this.saveOrUpdate(dishDto);
        //清理dish 对象的口味数据列表
        LambdaQueryWrapper<DishFlavor> lw = new LambdaQueryWrapper<>();
        lw.eq(DishFlavor::getDishId, dishDto.getId());
        dishFalvorService.remove(lw);
        //获取dishTo表中下的falvors 数据
        List<DishFlavor> listFalovrs = dishDto.getFlavors();
        listFalovrs.stream().map(item -> {
            DishFlavor dishFlavor = new DishFlavor();
            BeanUtils.copyProperties(item, dishFlavor);
            dishFlavor.setDishId(dishDto.getId());
            return dishFlavor;
        }).collect(Collectors.toList());
        return Result.success("修改成功");
    }

}

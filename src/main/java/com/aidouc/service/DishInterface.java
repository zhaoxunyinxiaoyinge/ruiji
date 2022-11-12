package com.aidouc.service;

import com.aidouc.common.Result;
import com.aidouc.controller.DishController;
import com.aidouc.domain.Dish;
import com.aidouc.dto.DishDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishInterface extends IService<Dish> {
    public void saveDish(DishDto dishDto);

    public Result<DishDto> getDetails(Long id);

    public Result<String> updates(DishDto dishDto);
}

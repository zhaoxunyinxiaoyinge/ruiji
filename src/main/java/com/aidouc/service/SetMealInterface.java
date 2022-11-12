package com.aidouc.service;

import com.aidouc.common.Result;
import com.aidouc.domain.Setmeal;
import com.aidouc.dto.SetmealDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SetMealInterface extends IService<Setmeal> {
    Result<String> saves(SetmealDto setmealDto);
    Result<String> deleteWidthIds(Long[] ids);
}

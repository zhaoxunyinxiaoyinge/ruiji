package com.aidouc.service;

import com.aidouc.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryInterface extends IService<Category> {
        public  void remove(Long id);
}

package com.aidouc.dao;

import com.aidouc.domain.Category;
import com.aidouc.domain.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.codehaus.jackson.map.MapperConfig;

@Mapper
public interface CategoryDao extends BaseMapper<Category>  {

}

package com.aidouc.service.Impl;

import com.aidouc.common.CustomExption;
import com.aidouc.common.Result;
import com.aidouc.dao.CategoryDao;
import com.aidouc.domain.Category;
import com.aidouc.domain.Dish;
import com.aidouc.domain.Setmeal;
import com.aidouc.service.CategoryInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class CategoryService extends ServiceImpl<CategoryDao, Category> implements CategoryInterface {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;

    // TODO(更具id删除品类和套餐)

    /**
     *
     * @param id
     * @return
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lw = new LambdaQueryWrapper<>();
        lw.eq(Dish::getId, id);
        int dashCount = dishService.count(lw);

        if (dashCount > 0) {
            throw new CustomExption("已关联菜品");
        }

        LambdaQueryWrapper<Setmeal> ls = new LambdaQueryWrapper<>();
        ls.eq(Setmeal::getId, id);
        int setmelCount = setMealService.count(ls);
        if (setmelCount > 0) {
            throw new CustomExption("已关联套餐");
        }
        super.removeById(id);
    }
}

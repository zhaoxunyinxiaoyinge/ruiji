package com.aidouc.controller;

import com.aidouc.common.Result;
import com.aidouc.dao.CategoryDao;
import com.aidouc.domain.Category;
import com.aidouc.domain.Employee;
import com.aidouc.service.Impl.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public Result<String> save(@RequestBody Category category){
        categoryService.save(category);
        return Result.success("新增成功后");
    }

    @GetMapping("/page")
    public Result<Page> getAll(int page,int pageSize){
        Page pagetion = new Page(page, pageSize);
        LambdaQueryWrapper<Category> lw = new LambdaQueryWrapper<>();
        lw.orderBy(true,true,Category::getSort);
        categoryService.page(pagetion, lw);
        return Result.success(pagetion);
    }

    @PutMapping
    public Result<String> updates(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.success("修改成功");
    }

    @DeleteMapping
    public  Result<String> deleteByIds(Long ids){
        log.info(ids+"ids的值");
        categoryService.remove(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/list")
    public Result<List<Category>> getDetail(String type){
    LambdaQueryWrapper<Category> lw=new LambdaQueryWrapper<Category>();
        System.out.println(type+"type");
        lw.eq(type!=null,Category::getType,type);
        List<Category> list=  categoryService.list(lw);
        return Result.success(list);
    }
}

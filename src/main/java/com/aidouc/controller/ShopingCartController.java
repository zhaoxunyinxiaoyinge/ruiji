package com.aidouc.controller;

import com.aidouc.common.Result;
import com.aidouc.domain.ShoppingCart;
import com.aidouc.service.Impl.ShopingCartService;
import com.aidouc.utils.BaseContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("//shoppingCart")
public class ShopingCartController {
    @Autowired
    private ShopingCartService shopingCartService;

    //查看购物车的数据
    @GetMapping("/list")
    public Result<List<ShoppingCart>> getList() {
        LambdaQueryWrapper<ShoppingCart> lt = new LambdaQueryWrapper<>();
        Long userId = BaseContext.getCurrentId();
        lt.eq(ShoppingCart::getUserId, userId);
        lt.orderBy(true, true, ShoppingCart::getCreateTime);
        return Result.success(shopingCartService.list(lt));
    }

    /**
     * @param shoppingCart
     * @return
     * @comment 添加套餐和菜品到购物车
     */
    @PostMapping("/add")
    public Result<ShoppingCart> addShopCart(@RequestBody ShoppingCart shoppingCart) {
        //将对当前用户的信息和订单进行绑定
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> lw = new LambdaQueryWrapper<>();
        lw.eq(ShoppingCart::getUserId, userId);
        //判断是否是套餐还是菜品
        if (shoppingCart.getSetmealId() != null) {
            lw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        } else {
            lw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        }
        ShoppingCart shop = shopingCartService.getOne(lw);
        if (shop != null) {
            int number = shop.getNumber();
            shop.setNumber(number + 1);
            shopingCartService.updateById(shop);
        } else {
            shoppingCart.setNumber(1);
            shopingCartService.save(shoppingCart);
            shop=shoppingCart;
        }
        return Result.success(shop);
    }

    /**
     * @comment 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public  Result<String> clear(){
        Long userId=BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart>lt= new LambdaQueryWrapper<>();
        lt.eq(ShoppingCart::getUserId,userId);
        shopingCartService.remove(lt);
        return Result.success("清空购物车成功");
    }

    @PostMapping("/sub")
    public Result<ShoppingCart> clearSub(@RequestBody ShoppingCart shoppingCart){
        Long userId=BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart>lt= new LambdaQueryWrapper<>();
        lt.eq(ShoppingCart::getUserId,userId);
        if(shoppingCart.getDishId()!=null){
            lt.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            lt.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart item = shopingCartService.getOne(lt);
        if(item.getNumber()>1){
          int number= item.getNumber();
            item.setNumber(number-1);
            shopingCartService.updateById(item);
        }else{
            shopingCartService.removeById(item.getId());
        }
        return Result.success(item);
    }

}

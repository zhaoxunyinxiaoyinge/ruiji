package com.aidouc.controller;

import com.aidouc.common.Result;
import com.aidouc.domain.AddressBook;
import com.aidouc.domain.ShoppingCart;
import com.aidouc.service.AddressBooksInterface;
import com.aidouc.service.Impl.AddressBooksService;
import com.aidouc.utils.BaseContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBooksController   {
   @Autowired
   private AddressBooksService addressBooksService;

   @GetMapping("/list")
    public Result<List<AddressBook>> getAdress(){
      LambdaQueryWrapper<AddressBook> lw= new LambdaQueryWrapper<>();
      lw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
      List<AddressBook> list=addressBooksService.list(lw);
      return Result.success(list);
   }

   @PostMapping
   public  Result<String> add(@RequestBody AddressBook addressBook){
      addressBook.setIsDefault(0);
      addressBook.setUserId(BaseContext.getCurrentId());
      addressBooksService.save(addressBook);
      return Result.success("新增成功");
   }

   /**
    * @commment 获取指定id的地址信息
    * @param id
    * @return
    */
   @GetMapping("/{id}")
   public Result<AddressBook> getSingleAdress(@PathVariable Long id){
    AddressBook addressBooks= addressBooksService.getById(id);
      return Result.success(addressBooks);
   }

   @PutMapping
   public  Result<String> updateSingleAdress(AddressBook addressBook){
      addressBooksService.updateById(addressBook);
      return Result.success("地址保存成功");
   }

   @DeleteMapping
   public  Result<String> deleteItem(Long ids){
      addressBooksService.removeById(ids);
      return Result.success("当前地址删除成功");
   }

   @PutMapping("/default")
   public  Result<String> setDefaultAddress(@RequestBody  AddressBook addressBook){
      Long currentId = BaseContext.getCurrentId();
      LambdaQueryWrapper<AddressBook> lw=new LambdaQueryWrapper<>();
      System.out.println(currentId+"currentId");
      lw.eq(AddressBook::getUserId,currentId);
      lw.eq(AddressBook::getIsDefault,1);
      AddressBook defaultAdress= addressBooksService.getOne(lw);
      if(defaultAdress!=null&&!defaultAdress.getId().equals(addressBook.getId())){
         System.out.println(defaultAdress+"地址");
         defaultAdress.setIsDefault(0);
         addressBooksService.updateById(defaultAdress);
      }
      addressBook.setIsDefault(1);
      System.out.println(addressBook.toString());
      addressBooksService.updateById(addressBook);
      return Result.success("设置默认地址成功");
   }

   @GetMapping("/default")
   public  Result<AddressBook> getDefaultAress(){
      Long currentId = BaseContext.getCurrentId();
      LambdaQueryWrapper<AddressBook> lw=new LambdaQueryWrapper<>();
      lw.eq(AddressBook::getUserId,currentId);
      lw.eq(AddressBook::getIsDefault,1);
     AddressBook addressBook= addressBooksService.getOne(lw);
     return Result.success(addressBook);
   }
}

package com.aidouc.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
//@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
//    private Map map = new HashMap();

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public Result() {
    }

    public static  <T> Result<T> success(T object) {
        Result<T>r=new Result<>();
        r.data=object;
        r.code=1;
        r.msg="请求成功";
        return r;
    }

    public  static <T> Result<T> error(String msg){
        Result r=new Result();
        r.data=null;
        r.code=-1;
        r.msg=msg;
        return r;
    }


    public  static <T> Result<T> loginerror(String msg ,int code){
        Result r=new Result();
        r.data=null;
        r.code=code;
        r.msg=msg;
        return r;
    }

}

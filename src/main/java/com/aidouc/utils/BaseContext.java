package com.aidouc.utils;

public class BaseContext {
    public static ThreadLocal<Long> thread= new ThreadLocal<Long>();
    public  static void setCurrentId(Long id){
            thread.set(id);
    }
    public  static Long getCurrentId(){
        return thread.get();
    }
}

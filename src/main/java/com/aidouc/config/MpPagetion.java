package com.aidouc.config;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class MpPagetion  {
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor(){
                MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
                mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
                return mybatisPlusInterceptor;
        }

}

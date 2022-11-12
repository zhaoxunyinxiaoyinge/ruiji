package com.aidouc.filter;

import com.aidouc.common.Result;
import com.aidouc.utils.BaseContext;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//过预期的的设置
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher path = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();

        //这里是白名单
        String[] urls = new String[]{
                "/employee/login",
                "/backend/**",
                "/employee/logout",
                "/front/**",
                "/user/login",
                "/user/sendMsg"
        };

        boolean flag = isCheckedPath(urls, url);
        if (flag) {
            log.info(request.getRequestURI() + "本次不需要请求");
            filterChain.doFilter(request, response);
            return;
        } else {
            //pc端的逻辑
            if (request.getSession().getAttribute("employee") != null) {
                log.info(request.getRequestURI() + "用户已经登录");
                BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
                filterChain.doFilter(request, response);
                return;
            }

            //移动端的逻辑
            if (request.getSession().getAttribute("user") != null) {
                log.info(request.getRequestURI() + "用户已经登录");
                BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
                filterChain.doFilter(request, response);
                return;
            }
            log.info(request.getRequestURI() + "用户未登录");
            response.getWriter().write(JSON.toJSONString(Result.loginerror("NOTLOGIN", 0)));
            return;

        }
    }

    /**
     * @param urls
     * @param url
     * @return
     */
    public boolean isCheckedPath(String[] urls, String url) {
        for (int i = 0; i < urls.length; i++) {
            boolean flag = path.match(urls[i], url);
            if (flag) {
                return true;
            }
        }
        return false;
    }
}

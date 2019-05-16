package com.wangchenglong.graduation.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @program: Graduation_WCL
 * @description: 不同角色跳转到不同页面
 * @author: Wangchenglong
 * @create: 2018-12-10 21:10
 **/

public class LoginSuccessHandle implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //获取到登陆者的权限，然后做跳转
        if (roles.contains("ROLE_ADMIN")){
            response.sendRedirect("/admin/admin_index");

            return;
        }else if (roles.contains("ROLE_USER")){

            response.sendRedirect("/index");
        }
        else {
            response.sendRedirect("/403");
        }

    }
}

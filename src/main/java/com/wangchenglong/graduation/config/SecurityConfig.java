package com.wangchenglong.graduation.config;

import com.wangchenglong.graduation.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * @program: Graduation_WCL
 * @description:
 * @author: Wangchenglong
 * @create: 2018-12-09 23:22
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义配置类
     *
     * @param http
     * @throws Exception
     */
    private static final String KEY = "1376834589@qq.com";
    @Bean
    UserDetailsService customUserService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/index","/**/*.png","/**/*.jpg").permitAll() // 都可以访问
                .antMatchers("/admin/**","/Admin/**").hasRole("ADMIN") // 需要相应的角色才能访问
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/index").failureUrl("/login-error").successHandler(new LoginSuccessHandle()).permitAll()
                .and().logout().logoutSuccessUrl("/index").invalidateHttpSession(true).permitAll()
                .and().exceptionHandling().accessDeniedPage("/403")
                .and().headers().frameOptions().sameOrigin()//项目中用到iframe嵌入网页，然后用到springsecurity就被拦截了 浏览器报错  x-frame-options deny
      //  原因是因为springSecurty使用X-Frame-Options防止网页被Frame
                .and().csrf().ignoringAntMatchers("/config")//忽略指定请求的csrf,此处忽略ueditor 富文本上传图片
                .and().rememberMe();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());

    }



}

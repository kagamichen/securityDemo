package org.chen.securitydemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.corba.se.idl.constExpr.And;
import org.chen.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

//@Configuration //demo01 基于内存的权限设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于内存的认证
      auth.inMemoryAuthentication()
              //设置该用户拥有的权限
              //基于内存的用户配直在配置角色时不需要添加“ROLE ”前缀，
              .withUser("root").password("123").roles("ADMIN","DBA")
              .and()
              .withUser("admin").password("123").roles("ADMIN","USER")
              .and()
              .withUser("chen").password("123").roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()//其他路径都需要登陆
                .and().formLogin().loginProcessingUrl("/login")//方便后续ajax或者移动端调用登录接口。
                .usernameParameter("name")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        Object principal = authentication.getPrincipal();
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        httpServletResponse.setStatus(200);
                        PrintWriter writer = httpServletResponse.getWriter();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("status",200);
                        map.put("msg",principal);
                        ObjectMapper om = new ObjectMapper();
                        writer.write(om.writeValueAsString(map));
                        writer.flush();
                        writer.close();
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
               response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                ObjectMapper om = new ObjectMapper();
                response.setStatus(401);
                HashMap<String, Object> map = new HashMap<>();
                map.put("status",401);
               map.put("msg","验证失败");
               writer.write(om.writeValueAsString(map));
               writer.flush();
               writer.close();
            }
        })
                .permitAll()//登陆接口不需要认证
                 .and().logout().logoutUrl("/logout")
                .and().csrf().disable();//关闭csrf 解决跨域问题

    }
}

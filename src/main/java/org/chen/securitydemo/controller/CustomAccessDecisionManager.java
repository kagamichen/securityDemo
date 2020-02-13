package org.chen.securitydemo.controller;

import jdk.nashorn.internal.runtime.FindProperty;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAccessDecisionManager implements AccessDecisionManager {
    /**
     * 判断当前登陆用户中是否具备当前请求url所需要的角色信息，不具备抛出异常
     * @param authentication 用户登陆信息
     * @param object   Filterlnvocation对象，获取请求对象
     * @param configAttributes  获取当前请求URL所需的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : configAttributes) {
            if ("ROLE_LOGIN".equals(configAttribute.getAttribute())&& authentication instanceof UsernamePasswordAuthenticationToken){
                return;
            }
            for (GrantedAuthority authority : authorities) {
            if (configAttribute.getAttribute().equals(authority.getAuthority()))
                return;
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

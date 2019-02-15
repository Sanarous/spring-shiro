package com.study.service;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

/**
 * @Author 左想
 * Shiro的业务逻辑层
 */
@Service
public class ShiroService {

    @RequiresRoles({"admin"})
    public void testMethod(){
        System.out.println("test...");
    }
}

package com.study.controller;

import com.study.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Shiro控制器
 * @Author zx
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/shiroLogin")
    public String login(@RequestParam("username") String username,@RequestParam("password")String password){
        Subject currentUser = SecurityUtils.getSubject();
        //测试当前用户是否已经被认证
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //设置记住我
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登陆失败" + ae.getMessage());
            }
        }
        return "redirect:/list.jsp";
    }

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(){
        shiroService.testMethod();
        return "redirect:/list.jsp";
    }
}

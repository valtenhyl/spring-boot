package com.valten.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权
 */
@RestController
public class AuthorizationController {

    @GetMapping("/cat")
    public String cat(){
        return "cat";
    }
    @GetMapping("/dog")
    public String dog(){
        return "dog";
    }
    @GetMapping("/sing")
    public String sing(){
        return "sing";
    }
    @GetMapping("/jump")
    public String jump(){
        return "jump";
    }
    @GetMapping("/rap")
    public String rap(){
        return "rap";
    }
    @GetMapping("/basketball")
    public String basketball(){
        return "basketball";
    }

  /*  @GetMapping("/dog")
    public String dog() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("dog")) {
            return "dog√";
        } else {
            return "dog×";
        }
    }

    @GetMapping("/cat")
    public String cat() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("cat")) {
            return "cat√";
        } else {
            return "cat×";
        }
    }

    // (2)注解式：注解@RequiresRoles("xxx")和@RequiresPermissions("xxx")进行角色和权限的校检
    @GetMapping("/sing")
    @RequiresRoles("cat")
    public String sing(){
        return "sing";
    }

    @GetMapping("/jump")
    @RequiresPermissions("jump")
    public String jump(){
        return "jump";
    }

    // (1)编程式：通过Subject的hasRole()进行角色的校检，通过isPermitted()进行权限的校检
    @GetMapping("/rap")
    public String rap(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isPermitted("rap")){
            return "rap";
        }else{
            return "没权限你Rap个锤子啊!";
        }
    }

    @GetMapping("/basketball")
    public String basketball(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isPermitted("basketball")){
            return "basketball";
        }else{
            return "你会打个粑粑球!";
        }
    }*/

    @GetMapping("/403")
    public String page_403(){
        return "403";
    }
}

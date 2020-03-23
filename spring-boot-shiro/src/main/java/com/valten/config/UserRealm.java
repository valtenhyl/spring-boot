package com.valten.config;

import com.valten.pojo.User;
import com.valten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权==》doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        //把登录用户塞进shiro的session shiro有自己独立的session~这也是为什么shiro可以脱离web使用
        Session session = subject.getSession();
        session.setAttribute("loginUser", currentUser);
        //如果没有权限，权限为null，就返回null
        if (currentUser.getPerms() == null){
            return null;
        }
        //设置权限~从数据库查寻
        info.addStringPermission(currentUser.getPerms());
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("执行认证==》AuthenticationInfo");
       //连接真实的数据库
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryByName(userToken.getUsername());
        //没有对应用户名的用户
        if(user == null){
            throw new UnknownAccountException();
        }
        //密码认证，由shiro做~ 为了防止密码泄露  第4个参数是realm名称
        return new SimpleAuthenticationInfo(user, userToken.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
    }
}

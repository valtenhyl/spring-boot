package com.valten.model;

import com.valten.util.PasswordGenerateUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro的自定义realm
 * Realm：域，Shiro从从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager
 * 要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * 授权
     *
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("调用了授权方法");
        // 获取当前登录的用户
        User user = (User) principal.getPrimaryPrincipal();
        // 通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 添加角色
        simpleAuthorizationInfo.addRole(user.getRole());
        // 添加权限
        simpleAuthorizationInfo.addStringPermissions(user.getPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1.获取用户输入的账号
        String username = (String) token.getPrincipal();
        // 2.通过username从数据库中查找到user实体
        User user = getUserByUserName(username);
        if (user == null) {
            return null;
        }
        // 3.通过SimpleAuthenticationInfo做身份处理
        // 第一个参数是从数据库中获取的User对象，第二个参数是数据库获取的密码，第三个参数是当前Realm的名称。
        // SimpleAuthenticationInfo simpleAuthenticationInfo =
        //        new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        // 4.用户账号状态验证等其他业务操作
        if (!user.getAvailable()) {
            throw new AuthenticationException("该账号已经被禁用");
        }
        // 5.返回身份处理对象
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟通过username从数据库中查找到user实体
     *
     * @param username
     * @return
     */
    private User getUserByUserName(String username) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 模拟数据库数据
     *
     * @return
     */
    /**
     * 模拟数据库数据
     *
     * @return
     */
    private List<User> getUsers() {
        /**
         * 模拟创建用户1
         */
        List<User> users = new ArrayList<>(2);
        String username1 = "张小黑的猫";
        String salt1 = Long.toString(System.currentTimeMillis());
        String password1 = PasswordGenerateUtil.getPassword(username1, "123qwe", salt1, 2);
        List<String> cat = new ArrayList<>(2);
        cat.add("sing");
        cat.add("rap");
        users.add(new User(username1, password1, salt1, true, "cat", cat));

        /***
         * 模拟创建用户2
         */
        String username2 = "张小黑的狗";
        String salt2 = Long.toString(System.currentTimeMillis());
        String password2 = PasswordGenerateUtil.getPassword(username2, "123qwe", salt2, 2);
        List<String> dog = new ArrayList<>(2);
        dog.add("jump");
        dog.add("basketball");
        users.add(new User(username2, password2, salt2, true, "dog", dog));

        System.out.println(users);
        return users;
    }
}

package com.study.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author 左想
 * 认证和授权只需要继承AuthorizingRealm即可
 * Realm相当于数据源dataSource，在这里要从数据库中获取用户权限数据
 */
public class ShiroRealm extends AuthorizingRealm {
    /**
     * controller中的token传到这里来了
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("[FirstRealm] doGetAuthenticationInfo:" + authenticationToken);
        //1.把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //2.从UsernamePasswordToken中获取username
        String username = token.getUsername();

        //3.从数据库中查询username对应的用户记录
        System.out.println("从数据库中获取username:" + username + "所对应的用户信息");

        //4.若用户不存在，则可以抛出认证UnknownAccountException异常
        String unknown = "unknown";
        if(unknown.equals(username)){
            throw new UnknownAccountException("用户不存在");
        }
        //5.根据用户信息的情况，决定是否需要抛出其它的异常
        String monster = "monster";
        if(monster.equals(username)){
            throw new LockedAccountException("用户被锁定!");
        }
        //6.根据用户的情况来构建AuthenticationInfo并返回,通常使用SimpleAuthenticationInfo
        //以下信息是从数据库中获取的
        //1.principal：认证的实体信息，可以是username，也可以是数据表对应的实体对象
        Object principal = username;
        //2.credentials：密码
        //"fc1709d0a95a6be30bc5926fdb7f22f4";
        Object credentials = null;
        String admin = "admin";
        String user = "user";
        if(admin.equals(username)){
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        }else if(user.equals(username)){
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }
        //3.realmName：当前realm对象的name，调用父类的getName()即可
        String realmName = getName();
        //4.盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info; //new SimpleAuthenticationInfo(principal,credentials,realmName);
        info = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt,realmName);
        return info;
    }

    /**
     * 1024次MD5加密后
     * @param args
     */
    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credential = "123456";
        Object salt = ByteSource.Util.bytes("user");
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, credential, salt, hashIterations);
        System.out.println(result);
    }

    /**
     * 用于授权的方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权方法调用了....");
        //1.从principalCollection中获取登陆用户信息
        Object principal = principalCollection.getPrimaryPrincipal();

        //2.利用登陆用户的信息来获取当前用户的角色或者权限（可能需要查询数据库）
        Set<String> roles = new HashSet<>();
        roles.add("user");
        String test = "admin";
        if(test.equals(principal)){
            roles.add("admin");
        }
        //3.创建SimpleAuthorizationInfo，并返回其roles对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //4.返回SimpleAuthorizationInfo对象
        return info;
    }
}

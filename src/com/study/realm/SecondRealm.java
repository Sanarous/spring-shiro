package com.study.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @Author zx
 * 测试多Realm环境
 */
public class SecondRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("[SecondRealm] doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        System.out.println("从数据库中获取username:" + username + "所对应的用户信息");
        String unknown = "unknown";
        if(unknown.equals(username)){
            throw new UnknownAccountException("用户不存在啊");
        }
        String monster = "monster";
        if(monster.equals(username)){
            throw new LockedAccountException("用户被锁定!");
        }
        //Object principal = username;
        Object credentials = null;
        //"fc1709d0a95a6be30bc5926fdb7f22f4";
        String admin = "admin";
        String user = "user";
        if(admin.equals(username)){
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        }else if(user.equals(username)){
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
        }
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info; //new SimpleAuthenticationInfo(principal,credentials,realmName);
        info = new SimpleAuthenticationInfo("secondRealmName", credentials,credentialsSalt,realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        Object credential = "123456";
        Object salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, credential, salt, hashIterations);
        System.out.println(result);
    }
}

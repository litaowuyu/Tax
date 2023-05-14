package com.service;


import java.util.List;
import java.util.Map;

public interface UserService {
    //注册
    public boolean register(String phone,String password);
    //登录
    public int  login(String phone, String password);
    //拉取用户信息
    public Map  userInfo( );
    //获取当前用户信息
    public Map getCurrentUser();
    //通过uni_id获取lv1 member
    public List loadMember(String uni_id);
    //获取lv2 member
    public List loadLv2Member(String phone);
    //注册VIP
    public boolean registerVIP(int price,String phoneNumber);
    //重置用户密码
    public int resetUserPassword(String phone, String password);
    //取消用户的vip_level，设置为VIP0
    public int cancelUserVIPLevel(String uni_id);

}

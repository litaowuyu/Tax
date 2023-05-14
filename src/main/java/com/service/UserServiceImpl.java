package com.service;


import com.common.exceptionHandle.iException;

import com.mapper.UserMapper;
import com.common.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;
    @Override
    public boolean register(String phone, String password) {
        /**
         * 先判断用户是否存在
         * 如果存在直接抛出错误代码
         * isEmpty()函数是判断变量时是否已经初始化，返回值时Boolean类型
         * 如果变量未初始化则返回true，否则返回false
         * */
        List<Map> users = userMapper.findByColumn("phone", phone);
        //如果不为空,则说明用户已经存在
        if (!users.isEmpty()) {
            throw new iException("USER_EXISTS");
        }
        //构建用户
        Map becomeUser=new HashMap();
        becomeUser.put("phone",phone);
        becomeUser.put("username",phone);
        //使用工具类将密码进行加密
        becomeUser.put("password", PasswordUtil.encode(password));
        //注册时候获取系统的唯一ID
        int random_id=this.generateUni_id();
        becomeUser.put("uni_id",random_id);
        //new Date().getTime()返回自从GMT 1970-01-01 00:00:00到此date对象上时间的毫秒数。
        becomeUser.put("join_time",new Date().getTime());
        int rows = userMapper.register(becomeUser);
        return rows > 0?true:false;
    }

    @Override
    public int login(String phone, String password) {
        List<Map> userList = userMapper.findByColumn("phone",phone);
        //如果没有查找到用户，抛登陆失败！
        if(userList.isEmpty()){
            throw new iException(44,"LOGIN_FAIL");
        }
        System.out.println("查询到的用户"  +  userList.get(0));
        Map user =userList.get(0);
       // User user= (User) userList.get(0);
        if (PasswordUtil.encode(password).equals(user.get("password"))){
            return (int) user.get("id");
        }

        return 0;
    }


    /**
     * 先获取通过JWT获取token，将用户的userID存入token
     * 然后登录拦截，拦截token，
     * 解析token，如果token合法就放行
     * 获取token中拿到的数据存入setAttribute（）
     * 然后再通过getAttribute()拿到userID
     * 最后通过UserID进行查询操作
     * 查询结果通过Result.data()方式返回给前端
     * 先判断用户是否登录
     * 如果登录了则拉取数据，反之跳到登陆界面
     */
    @Override
    public Map userInfo() {
        System.out.println("UserServiceImpl中的UserID-------------"+request.getAttribute("userID"));

        int userID = (int) request.getAttribute("userID");

        return userMapper.findByID(userID);

    }

    @Override
    public Map getCurrentUser() {
        int user_id= (int) request.getAttribute("userID");
        return userMapper.findByID(user_id);
    }



    /**
     * 伸成一个系统唯一的七位数uni_id
     * 遍历数据库查询是否存在
     * 不存在的话uni_id赋给注册用户
     */
    public int generateUni_id(){
        int random= (int) (((Math.random())*9+1)*1000000);
        List<Map> users=userMapper.findByColumn("uni_id",String.valueOf(random));
        if(!users.isEmpty()){
            return this.generateUni_id();
        }
        return random;
    }
    @Override
    public List loadMember(String uni_id) {
        List member = userMapper.findByColumn("parent_id", uni_id);
        return member;
    }

    @Override
    public List loadLv2Member(String phone) {

        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public boolean registerVIP(int price, String phoneNumber) {
        Map userInfo = userMapper.findByPhone(phoneNumber);
        System.out.println();
        int userBalance = Integer.parseInt(userInfo.get("balance").toString());
        int vip_levelTable = (int)userInfo.get("vip_level");
        if (userBalance>=price*100){
            userBalance = userBalance-price*100;
            if (price==2){
                int vip_level=1;
                if (vip_levelTable>=vip_level){
                    return false;
                }
                userMapper.updateVipLevel(userBalance,vip_level,phoneNumber);
                return true;
            }else if(price==10){
                int vip_level=2;
                if (vip_levelTable>=vip_level){
                    return false;
                }
                userMapper.updateVipLevel(userBalance,vip_level,phoneNumber);
                return true;
            }else if(price==50){
                int vip_level=3;
                if (vip_levelTable>=vip_level){
                    return false;
                }
                userMapper.updateVipLevel(userBalance,vip_level,phoneNumber);
                return true;
            }else if(price==100){
                int vip_level=4;
                if (vip_levelTable>=vip_level){
                    return false;
                }
                userMapper.updateVipLevel(userBalance,vip_level,phoneNumber);
                return true;
            }
        }
        return false;
    }
    @Override
    public int resetUserPassword(String phone, String password) {

        return userMapper.updateUserPassword(phone, password);
    }
    @Override
    public int cancelUserVIPLevel(String uni_id) {
        return userMapper.resetUserVipLevel(uni_id);
    }
}


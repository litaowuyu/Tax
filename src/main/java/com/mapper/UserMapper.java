package com.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper{
    //通过电话号来获取lv2 member
    List selectUserByPhone(String phone);
    Map findByPhone(String phoneNumber);

    int updateVipLevel(@Param("balance") int balance,
                       @Param("vip_level") int vip_level,
                       @Param("phone") String phoneNumber);
    int updateUserPassword(String phone, String password);
    int resetUserVipLevel(String uni_id);

}

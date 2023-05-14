package com.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface BaseMapper {
    public   int register(Object object);

    //  通过一个字段找用户，返回一个List集合接收
    public List findByColumn(@Param("column") String column,@Param("value") String value);

    public Map findByID(int userID);

    public List getList();
    //查询所有订单，用来进行订单的定时任务
    public List findByWhereWithList(HashMap hashMap);
}

package com.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsMapper extends BaseMapper{
    public List getAllGoods();



}

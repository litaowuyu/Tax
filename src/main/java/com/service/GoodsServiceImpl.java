package com.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Map> getGoodsList(int pageNum, int pageSize, int vipID) {
        /**
         * pageNum:当前页
         * pageSize:每页的数量
         * startPage()用来进行开始分页，一定要在查询前边开启分页
         */

        PageHelper.startPage(pageNum, pageSize);
        //依靠vipID 来查询数据
        List<Map> result = goodsMapper.findByColumn("vip_level", String.valueOf(vipID));
        //将查询的数据传给pageInfo
        PageInfo pageInfo = new PageInfo<>(result);
        //通过pageInfo.getList()拿到分页后的数据
        return pageInfo.getList();
    }

    @Override
    public List<Map> getGoodsList(int pageNum, int pageSize) {
        /**
         * pageNum:当前页
         * pageSize:每页的数量
         * startPage()用来进行开始分页，一定要在查询前边开启分页
         */

        PageHelper.startPage(pageNum, pageSize);
        //依靠vipID 来查询数据
        List<Map> result = goodsMapper.getAllGoods();
        //将查询的数据传给pageInfo
        PageInfo pageInfo = new PageInfo<>(result);
        //通过pageInfo.getList()拿到分页后的数据
        return pageInfo.getList();
    }



}

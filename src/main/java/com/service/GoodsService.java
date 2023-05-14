package com.service;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    public List<Map> getGoodsList( int pageNum,int pageSize,int vipID);

    public List<Map> getGoodsList(int pageNum,int pageSize);

}

package com.service;

import com.mapper.VipLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class VipLevelServiceImpl implements VipLevelService {

   @Autowired
   private VipLevelMapper vipLevelMapper;

    @Override
    public List<Map> getList() {
        return vipLevelMapper.getList();

    }
}

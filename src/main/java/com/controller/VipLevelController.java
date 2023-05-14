package com.controller;

import com.common.exceptionHandle.Result;
import com.service.VipLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VipLevelController {
    @Autowired
    VipLevelService vipLevelService;
    @GetMapping("noAuth/vipList")
    public Map getVIP() {
        return Result.data(vipLevelService.getList());

    }
}

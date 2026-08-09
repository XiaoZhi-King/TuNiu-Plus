package com.tuniu.ticket.controller;

import com.tuniu.common.domain.City;
import com.tuniu.common.vo.Result;
import com.tuniu.ticket.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 城市查询接口
 * 路径与前端历史调用保持一致：/city/list、/city/which
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * 全部城市列表
     * 注意：前端直接以 List 形式消费（res.data 即数组），这里保持原返回结构
     */
    @GetMapping("/list")
    public List<City> list() {
        return cityService.list();
    }

    /**
     * 按名称查询城市
     */
    @GetMapping("/which")
    public Result selectByName(@RequestParam("name") String name) {
        return Result.ok(cityService.selectByName(name));
    }
}

package com.tuniu.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuniu.common.domain.Passenger;
import com.tuniu.common.vo.Result;
import com.tuniu.user.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 乘车人接口（路径与旧版完全一致，返回 MyBatis-Plus IPage 与前端 JSON 结构兼容）
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @RequestMapping("/list")
    public IPage<Passenger> list(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                 @RequestParam(name = "userId") Integer userId,
                                 @RequestParam(name = "name", defaultValue = "") String name) {
        return passengerService.list(pageNum, pageSize, userId, name);
    }

    @RequestMapping("/del")
    public String delete(Integer id) {
        return passengerService.deleteById(id) > 0 ? "删除成功" : "删除失败";
    }

    @RequestMapping("/save")
    public Result save(@RequestBody Passenger passenger) {
        if (passengerService.save(passenger) > 0) {
            return Result.ok(200, "成功");
        }
        return Result.error(500, "失败");
    }

    /**
     * 按 ID 集合批量查询乘车人（供订单服务通过 OpenFeign 调用）
     * 仅限内部服务调用，网关不对外暴露
     */
    @PostMapping("/listByIds")
    public List<Passenger> listByIds(@RequestBody List<Integer> ids) {
        return passengerService.listByIds(ids);
    }
}

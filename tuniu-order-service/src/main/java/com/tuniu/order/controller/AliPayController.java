package com.tuniu.order.controller;

import com.tuniu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟支付接口（练习：请自行实现以下能力）
 * <p>
 * TODO 实现要求：
 * 1) /pay：
 *      - 调用 orderService.paySuccess(orderId) 把订单状态从 "待支付" 置为 "已支付"
 *      - 按成功/失败生成 HTML 结果页（建议返回一张带 Element-Plus 风格的卡片：标题、金额、返回按钮）
 *      - produces = MediaType.TEXT_HTML_VALUE；前端使用 window.open 跳转打开
 * 2) /notify：保留原路径，返回 success / fail（模拟支付宝异步回调，前端不直接调用）
 * 3) 提示：可以写一个私有方法 buildHtml(title, message, color) 统一拼 HTML
 */
@RestController
@RequestMapping("/alipay")
@Slf4j
public class AliPayController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/pay", produces = MediaType.TEXT_HTML_VALUE)
    public String pay(@RequestParam("orderId") Integer orderId,
                      @RequestParam(value = "totalPrice", required = false) String totalPrice) {
        // TODO: 自行实现（调用 orderService.paySuccess + 拼 HTML 结果页）
        throw new UnsupportedOperationException("练习待实现：AliPayController.pay");
    }

    @RequestMapping("/notify")
    public String notify(@RequestParam("orderId") Integer orderId) {
        // TODO: 自行实现
        throw new UnsupportedOperationException("练习待实现：AliPayController.notify");
    }
}

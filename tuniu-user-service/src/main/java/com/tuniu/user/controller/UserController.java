package com.tuniu.user.controller;

import com.tuniu.common.domain.User;
import com.tuniu.common.util.JwtUtil;
import com.tuniu.common.util.SaltMD5Util;
import com.tuniu.common.vo.Result;
import com.tuniu.common.vo.TokenUserIdVo;
import com.tuniu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口（路径与旧版完全一致，前端无需改动）
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/checkAccount")
    public boolean checkUsername(String account) {
        User user = userService.findByAccount(account);
        return user != null;
    }

    @RequestMapping("/checkPassword")
    public boolean checkPassword(String account, String password) {
        User user = userService.findByAccount(account);
        return user != null && SaltMD5Util.verifySaltPassword(password, user.getPassword());
    }

    @RequestMapping("/checkToken")
    public boolean checkToken(@RequestHeader("token") String token) {
        return JwtUtil.checkToken(token);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String account = user.getAccount();
        String password = user.getPassword();
        User dbUser = userService.findByAccount(account);
        if (dbUser == null) {
            return Result.error(500, "用户不存在");
        }
        if (!SaltMD5Util.verifySaltPassword(password, dbUser.getPassword())) {
            return Result.error(500, "密码错误");
        }
        TokenUserIdVo vo = new TokenUserIdVo();
        vo.setToken(JwtUtil.getToken(account));
        vo.setUserId(dbUser.getId());
        vo.setName(dbUser.getName());
        log.info("[用户服务] 登录成功 account={} userId={}", account, dbUser.getId());
        return Result.ok(vo);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String account = userService.register(user);
        return Result.ok(200, "注册成功,你的账号为:" + account + "请牢记");
    }

    @RequestMapping("/getAccount")
    public String getAccount(Integer id) {
        return userService.getAccount(id);
    }

    @GetMapping("/checkName")
    public boolean checkName(@RequestParam String name) {
        // true 表示昵称可用（未被占用）
        return userService.isNameAvailable(name);
    }
}

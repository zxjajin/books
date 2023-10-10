package com.ajin.book.controller;


import com.ajin.book.common.Result;
import com.ajin.book.entity.User;
import com.ajin.book.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService service;

    /**
     * 用户登录
     * @param user 用户名和密码
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User user1 = service.getOne(new QueryWrapper<User>().eq("username", user.getUsername()).eq("password", user.getPassword()));
        Assert.notNull(user1,"用户名或密码错误！");
        return Result.succ(200,"登录成功",user1);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/resign")
    public Result resign(@RequestBody User user){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        LocalDateTime dateTime = LocalDateTime.now();
//        String formattedDateTime = dateTime.format(formatter);
        user.setCreateTime(LocalDateTime.now());
        boolean result = service.save(user);
        Assert.isTrue(result,"注册失败！");
        return Result.succ(result);
    }

    /**
     * 查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable(name = "id") Integer id){
        User user = service.getById(id);
        Assert.notNull(user,"用户不存在！");
        return Result.succ(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        boolean result = service.updateById(user);
        Assert.isTrue(result,"修改失败！");
        return Result.succ(result);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        boolean result = service.removeById(id);
        Assert.isTrue(result,"删除失败！");
        return Result.succ(result);
    }

}

package com.ajin.book.controller;


import cn.hutool.crypto.SecureUtil;
import com.ajin.book.common.Result;
import com.ajin.book.dto.LoginUser;
import com.ajin.book.entity.Systemlog;
import com.ajin.book.entity.User;
import com.ajin.book.service.UserService;
import com.ajin.book.service.impl.SystemlogServiceImpl;
import com.ajin.book.service.impl.UserDetailsServiceImpl;
import com.ajin.book.util.JwtUtil;
import com.ajin.book.util.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

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

    @Autowired
    private SystemlogServiceImpl systemlogService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    /**
     * 用户登录
     * @param user 用户名和密码
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
//        User user1 = service.getOne(new QueryWrapper<User>().eq("username", user.getUsername()).eq("password", SecureUtil.md5(user.getPassword())));
//        Assert.notNull(user1,"用户名或密码错误！");
//        return Result.succ(200,"登录成功",user1);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        Systemlog systemlog = new Systemlog(loginUser.getUser().getUserId(), LocalDateTime.now(), "登录成功", "无异常");
        systemlogService.save(systemlog);
        //把token响应给前端
        HashMap<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("user",loginUser);
        return Result.succ(200,"登陆成功",map);
    }


    @GetMapping("/logout/{id}")
    public Result logout(@PathVariable("id") Integer userid) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        Integer userid = loginUser.getUser().getUserId();
        redisCache.deleteObject("login:"+userid);
        Systemlog systemlog = new Systemlog(userid, LocalDateTime.now(), "退出登录", "无异常");
        systemlogService.save(systemlog);
        return Result.succ("退出成功");
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
        Assert.notNull(user,"用户信息不能为空");
        User u = service.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        Assert.isNull(u,"用户名已经存在");
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(SecureUtil.md5(user.getPassword()));
        boolean result = service.save(user);
        Assert.isTrue(result,"注册失败！");
        Systemlog systemlog = new Systemlog(user.getUserId(), LocalDateTime.now(), "注册账号成功", "无异常");
        systemlogService.save(systemlog);
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
//        System.out.println(authenticationManager);
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
        Assert.notNull(user,"用户信息不能为空");
        User u = service.getOne(new QueryWrapper<User>().eq("username", user.getUsername()).notIn("user_id",user.getUserId()));
        Assert.isNull(u,"用户名已经存在");
        if(user.getPassword()==""){
            user.setPassword(null);
        }
        if(user.getPassword()!=null && user.getPassword()!=""){
            user.setPassword(SecureUtil.md5(user.getPassword()));
        }
        boolean result = service.updateById(user);
        Assert.isTrue(result,"修改失败！");
        Systemlog systemlog = new Systemlog(user.getUserId(), LocalDateTime.now(), "用户修改信息成功", "无异常");
        systemlogService.save(systemlog);
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
        Systemlog systemlog = new Systemlog(id, LocalDateTime.now(), "账号注销了", "无异常");
        systemlogService.save(systemlog);
        return Result.succ(result);
    }

}

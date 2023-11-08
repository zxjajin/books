package com.ajin.book.controller;


import cn.hutool.crypto.SecureUtil;
import com.ajin.book.common.Result;
import com.ajin.book.dto.LoginAdmin;
import com.ajin.book.dto.LoginUser;
import com.ajin.book.entity.Admin;
import com.ajin.book.entity.Systemlog;
import com.ajin.book.service.impl.AdminServiceImpl;
import com.ajin.book.service.impl.SystemlogServiceImpl;
import com.ajin.book.util.JwtUtil;
import com.ajin.book.util.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SystemlogServiceImpl systemlogService;

    @PostMapping(value = "/upload")
    public void uploadFile(@RequestParam("files") MultipartFile[] files) throws IOException {
        // 1. 用数组MultipartFile[]来表示多文件,所以遍历数组,对其中的文件进行逐一操作
        for (MultipartFile file : files) {
            // 2. 通过一顿file.getXXX()的操作,获取文件信息。
            // 2.1 这里用文件名举个栗子
            String filename = file.getOriginalFilename();
            // 3. 接下来调用方法来保存文件到本地磁盘, 返回的是保存后的文件路径
            String filePath = savaFileByNio((FileInputStream) file.getInputStream(), filename);
            // 4. 保存文件信息到数据库
            System.out.println(filePath);
            // 4.1 搞个实体类，把你需要的文件信息保存到实体类中
            // 4.2 调用Service层或者Dao层，保存数据库即可。
        }
    }

    public static String savaFileByNio(FileInputStream fis, String fileName) {
        // 这个路径最后是在: 你的项目路径/FileSpace  也就是和src同级
//        String fileSpace = System.getProperty("user.dir") + File.separator + "FileSpace";
//        String fileSpace = "/opt" + File.separator + "static";
        String fileSpace = "F:\\daima\\books\\static";
        String path = fileSpace + File.separator + fileName;
        // 判断父文件夹是否存在
        File file = new File(path);
        if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        // 通过NIO保存文件到本地磁盘
        try {
            FileOutputStream fos = new FileOutputStream(path);
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 管理员登录
     * @param admin 用户名和密码
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
//        Admin admin1 = service.getOne(new QueryWrapper<Admin>().eq("admin_name", admin.getAdminName()).eq("admin_password", SecureUtil.md5(admin.getAdminPassword())));
//        Assert.notNull(admin1,"用户名或密码错误！");
//        Systemlog systemlog = new Systemlog(admin1.getAdminId(), LocalDateTime.now(), "管理员登录成功");
//        systemlogService.save(systemlog);
//        return Result.succ(200,"登录成功",admin1);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(admin.getAdminName(),admin.getAdminPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用id生成token
        LoginAdmin loginAdmin = (LoginAdmin) authenticate.getPrincipal();
        String Id = loginAdmin.getAdmin().getAdminId().toString();
        String jwt = JwtUtil.createJWT(Id);
        //authenticate存入redis
        redisCache.setCacheObject("admin:"+Id,loginAdmin);
        Systemlog systemlog = new Systemlog(loginAdmin.getAdmin().getAdminId(), LocalDateTime.now(), "登录成功", "无异常");
        systemlogService.save(systemlog);
        //把token响应给前端
        HashMap<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("admin",loginAdmin);
        return Result.succ(200,"登陆成功",map);
    }

    /**
     * 管理员注册
     * @param admin
     * @return
     */
    @PostMapping("/resign")
    public Result resign(@RequestBody Admin admin){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//        LocalDateTime dateTime = LocalDateTime.now();
//        String formattedDateTime = dateTime.format(formatter);
        Assert.notNull(admin,"管理员账号或密码不能为空");
        Admin name = service.getOne(new QueryWrapper<Admin>().eq("admin_name", admin.getAdminName()));
        Assert.isNull(name,"管理员账号已经存在");
        admin.setCreateTime(LocalDateTime.now());
        admin.setAdminPassword(SecureUtil.md5(admin.getAdminPassword()));
        boolean result = service.save(admin);
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
        Admin admin = service.getById(id);
        Assert.notNull(admin,"管理员不存在！");
        return Result.succ(admin);
    }

    /**
     * 修改管理员信息
     * @param admin
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Admin admin){
        Assert.notNull(admin,"信息不能为空");
        Admin u = service.getOne(new QueryWrapper<Admin>().eq("admin_name", admin.getAdminName()).notIn("admin_id",admin.getAdminId()));
        Assert.isNull(u,"用户名已经存在");
        if(admin.getAdminPassword()==""){
            admin.setAdminPassword(null);
        }
        if(admin.getAdminPassword()!=null && admin.getAdminPassword()!=""){
            admin.setAdminPassword(SecureUtil.md5(admin.getAdminPassword()));
        }
        boolean result = service.updateById(admin);
        Assert.isTrue(result,"修改失败！");
        Systemlog systemlog = new Systemlog(admin.getAdminId(), LocalDateTime.now(), "管理员修改信息成功");
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
        return Result.succ(result);
    }

    @GetMapping("/logout/{id}")
    public Result logout(@PathVariable("id") Integer adminId) {
        redisCache.deleteObject("admin:"+adminId);
        return Result.succ("退出成功");
    }

}

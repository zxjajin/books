package com.ajin.book.controller;


import com.ajin.book.common.Result;
import com.ajin.book.dto.SystemlogDto;
import com.ajin.book.entity.Admin;
import com.ajin.book.entity.Systemlog;
import com.ajin.book.entity.User;
import com.ajin.book.service.UserService;
import com.ajin.book.service.impl.AdminServiceImpl;
import com.ajin.book.service.impl.SystemlogServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/systemlog")
public class SystemlogController {

    @Autowired
    private SystemlogServiceImpl service;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminServiceImpl adminService;


    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id){
        Systemlog systemlog = service.getById(id);
        return Result.succ(systemlog);
    }

    @GetMapping("/list")
    public Result list(){
        List<Systemlog> list = service.list(new QueryWrapper<Systemlog>().orderByDesc("operation_time"));
        List<SystemlogDto> systemlogDtoList = new ArrayList<>();
        for(Systemlog systemlog:list){
            SystemlogDto systemlogDto = new SystemlogDto();
            BeanUtils.copyProperties(systemlog,systemlogDto);
            if(!StringUtils.isEmpty(systemlog.getAdminId())){
                Admin admin = adminService.getById(systemlog.getAdminId());
                systemlogDto.setUsername(admin.getAdminName());
            }else {
                User user = userService.getById(systemlog.getUserId());
                systemlogDto.setUsername(user.getUsername());
            }
            systemlogDtoList.add(systemlogDto);
        }
        return Result.succ(systemlogDtoList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Systemlog systemlog){
        boolean save = service.save(systemlog);
        Assert.notNull(save,"添加失败！");
        return Result.succ(save);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        boolean result = service.removeById(id);
        Assert.isTrue(result,"删除失败！");
        return Result.succ(result);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Systemlog systemlog){
        boolean result = service.updateById(systemlog);
        Assert.isTrue(result,"修改失败！");
        return Result.succ(result);
    }

}

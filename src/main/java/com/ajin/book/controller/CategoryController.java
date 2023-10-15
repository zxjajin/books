package com.ajin.book.controller;


import com.ajin.book.common.Result;
import com.ajin.book.entity.Category;
import com.ajin.book.entity.Systemlog;
import com.ajin.book.service.CategoryService;
import com.ajin.book.service.impl.CategoryServiceImpl;
import com.ajin.book.service.impl.SystemlogServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 类别表 前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl service;

    @Autowired
    private SystemlogServiceImpl systemlogService;

    @GetMapping("/cates")
    public Result cates(){
        List<Category> list = service.list();
        return Result.succ(list);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Category> page = new Page<>(currentPage,10);
        IPage<Category> categoryIPage = service.page(page);
        return Result.succ(categoryIPage);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Integer id){
        Category category = service.getById(id);
        return Result.succ(category);
    }
    @PostMapping("/add/{adminId}")
    public Result add(@RequestBody Category category,@PathVariable("adminId") Integer id){
        boolean save = service.save(category);
        Assert.notNull(save,"添加失败！");
        Systemlog systemlog = new Systemlog(id, LocalDateTime.now(), "管理员添加分类成功");
        systemlogService.save(systemlog);
        return Result.succ(save);
    }

    @DeleteMapping("/{id}/{adminId}")
    public Result delete(@PathVariable("id") Integer id,@PathVariable("adminId") Integer adminId){
        boolean result = service.removeById(id);
        Assert.isTrue(result,"删除失败！");
        Systemlog systemlog = new Systemlog(adminId, LocalDateTime.now(), "管理员删除分类成功");
        systemlogService.save(systemlog);
        return Result.succ(result);
    }

    @PutMapping("/update/{adminId}")
    public Result update(@RequestBody Category category,@PathVariable("adminId") Integer id){
        boolean result = service.updateById(category);
        Assert.isTrue(result,"修改失败！");
        Systemlog systemlog = new Systemlog(id, LocalDateTime.now(), "管理员修改分类成功");
        systemlogService.save(systemlog);
        return Result.succ(result);
    }


}

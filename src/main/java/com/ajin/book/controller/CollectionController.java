package com.ajin.book.controller;


import com.ajin.book.common.Result;
import com.ajin.book.entity.Book;
import com.ajin.book.entity.Collection;
import com.ajin.book.entity.Systemlog;
import com.ajin.book.service.impl.BookServiceImpl;
import com.ajin.book.service.impl.CollectionServiceImpl;
import com.ajin.book.service.impl.SystemlogServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import sun.rmi.runtime.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-12
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionServiceImpl service;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private SystemlogServiceImpl systemlogService;

    @GetMapping("/{userId}")
    public Result getCollection(@PathVariable("userId") Integer userId){

        // 对用户提供的输入进行验证或过滤
        if (userId == null || userId <= 0) {
            return Result.fail("用户不存在！");
        }
        List<Collection> list = service.list(new QueryWrapper<Collection>().eq("user_id", userId));
        List<Integer> bookIds = list.stream()
                .map(Collection::getBookId)
                .collect(Collectors.toList());
//        List<Integer> bookIds = new ArrayList<>();
//        for (Collection collection : list) {
//            bookIds.add(collection.getBookId());
//        }
        List<Book> bookList = bookService.list(new QueryWrapper<Book>().in("book_id", bookIds));
        return Result.succ(bookList);
    }

    @PostMapping("/hope")
    public Result hope(@RequestBody Collection collection){
        if(collection.getBookId()!=null && collection.getUserId()!=null){
            boolean result = service.save(collection);
            Assert.isTrue(result,"收藏失败");
            Systemlog systemlog = new Systemlog(collection.getUserId(), LocalDateTime.now(), "收藏图书成功", "无异常");
            systemlogService.save(systemlog);
            return Result.succ("收藏成功");
        }
        Systemlog systemlog = new Systemlog(collection.getUserId(), LocalDateTime.now(), "收藏图书失败", "无异常");
        systemlogService.save(systemlog);
        return Result.fail("收藏失败");
    }

    @DeleteMapping("/{bookId}/{userId}")
    public Result cancel(@PathVariable("bookId") Integer bookId,@PathVariable("userId") Integer userId){
        if(bookId!=null && userId!=null){
        boolean result = service.remove(new QueryWrapper<Collection>().eq("user_id", userId).eq("book_id", bookId));
        Assert.isTrue(result,"取消收藏失败");
        Systemlog systemlog = new Systemlog(userId, LocalDateTime.now(), "取消收藏图书", "无异常");
        systemlogService.save(systemlog);
        return Result.succ("取消收藏成功");
        }
        Systemlog systemlog = new Systemlog(userId, LocalDateTime.now(), "收藏图书失败", "无异常");
        systemlogService.save(systemlog);
        return Result.fail("取消收藏失败");
    }

    @GetMapping("/get")
    public Result get(@RequestParam("userId") Integer userId,@RequestParam("bookId") Integer bookId){
        if(bookId!=null && userId!=null){
            Collection serviceOne = service.getOne(new QueryWrapper<Collection>().eq("user_id", userId).eq("book_id", bookId));
            if(serviceOne!=null) {
                return Result.succ(1);
            }
        }
        return Result.succ(0);
    }


}

package com.ajin.book.controller;


import com.ajin.book.common.Result;
import com.ajin.book.entity.Book;
import com.ajin.book.entity.Collection;
import com.ajin.book.service.impl.BookServiceImpl;
import com.ajin.book.service.impl.CollectionServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

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

}

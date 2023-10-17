package com.ajin.book.controller;


import com.ajin.book.common.Result;
import com.ajin.book.dto.ReadinghistoryDto;
import com.ajin.book.entity.Book;
import com.ajin.book.entity.Readinghistory;
import com.ajin.book.service.BookService;
import com.ajin.book.service.impl.ReadinghistoryServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/readinghistory")
public class ReadinghistoryController {

    @Autowired
    private ReadinghistoryServiceImpl service;

    @Autowired
    private BookService bookService;

    /**
     * 查询用户阅读记录
     * @param id
     * @return
     */
    @GetMapping("/list/{userId}")
    public Result list(@PathVariable("userId") Integer id){
        List<Readinghistory> readinghistoryList = service.list(new QueryWrapper<Readinghistory>().eq("user_id", id).orderByDesc("read_time"));
        List<ReadinghistoryDto> readinghistoryDtoList = new ArrayList<ReadinghistoryDto>();
        for(Readinghistory read:readinghistoryList){
            ReadinghistoryDto readinghistoryDto = new ReadinghistoryDto();
            BeanUtils.copyProperties(read,readinghistoryDto);
            Book book = bookService.getById(read.getBookId());
            BeanUtils.copyProperties(book,readinghistoryDto);
            readinghistoryDtoList.add(readinghistoryDto);
        }
        return Result.succ(readinghistoryDtoList);
    }


}

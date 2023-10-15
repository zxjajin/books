package com.ajin.book.controller;


import cn.hutool.core.util.ArrayUtil;
import com.ajin.book.common.Result;
import com.ajin.book.dto.BookDto;
import com.ajin.book.entity.Book;
import com.ajin.book.entity.Category;
import com.ajin.book.entity.Readinghistory;
import com.ajin.book.entity.Systemlog;
import com.ajin.book.service.impl.BookServiceImpl;
import com.ajin.book.service.impl.CategoryServiceImpl;
import com.ajin.book.service.impl.ReadinghistoryServiceImpl;
import com.ajin.book.service.impl.SystemlogServiceImpl;
import com.ajin.book.util.FileUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 书籍表 前端控制器
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookServiceImpl service;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private SystemlogServiceImpl systemlogService;

    @Autowired
    private ReadinghistoryServiceImpl readservice;

    @GetMapping("/books")
    public Result books() {
        List<Book> list = service.list();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : list) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            Category category = categoryService.getById(bookDto.getCategoryId());
            bookDto.setCategoryName(category.getCategoryName());
            bookDtoList.add(bookDto);
        }
        return Result.succ(bookDtoList);
    }

    @GetMapping("/book/{id}")
    public Result book(@PathVariable("id") Integer id){
        BookDto bookDto = service.getBookDto(id);
        return Result.succ(bookDto);
    }

    @GetMapping("/type/{id}")
    public Result bookBycategory(@PathVariable("id") Integer id,@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Book> page = new Page<>(currentPage, 6);
        IPage<Book> bookIPage = service.page(page, new QueryWrapper<Book>().eq("category_id", id).orderByDesc("rating"));
        return Result.succ(bookIPage);
    }

    @GetMapping("/{id}")
    public Result book(@PathVariable("id") Integer id,Integer userId){
        Book book = service.getById(id);
        if(book!=null && userId != null){
            Readinghistory readinghistory = new Readinghistory(userId, id, LocalDateTime.now());
            boolean save = readservice.save(readinghistory);
        }
        return Result.succ(book);
    }

    /**
     * 根据search进行模糊查询
     * @param currentPage 当前页数默认值为1
     * @param search 要查询内容
     * @return
     */
    @GetMapping("/search")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam String search){
        search = search.trim();
        if(StringUtils.isNotEmpty(search)){
            Page page = new Page(currentPage, 6);
            IPage pageDate = service.page(page, new QueryWrapper<Book>().like("title", search).or().like("author", search));
            return Result.succ(pageDate);
        }
        return list(1);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<Book> page = new Page<>(currentPage, 6);
        IPage<Book> bookIPage = service.page(page);
        return Result.succ(bookIPage);
    }

    @PostMapping("/add/{adminId}")
    public Result add(@RequestBody Book book,@PathVariable("adminId") Integer adminId){
        Assert.notNull(adminId,"权限不够！");
        Assert.notNull(book,"图书信息不能为空！");
        if(book.getCategoryId()!=null && book.getAuthor()!=""
        && book.getDescription()!="" && book.getPublisher()!=""
                && book.getRating()!=null && book.getReleaseDate()!=null &&
        book.getCoverImage()!="" && book.getCoverImage()!=null){
            book.setCoverImage("/static/"+book.getCoverImage());
            boolean result = service.save(book);
            Assert.notNull(result,"添加失败！");
            Systemlog systemlog = new Systemlog(adminId, LocalDateTime.now(), "管理员添加图书成功");
            systemlogService.save(systemlog);
            return Result.succ(result);
        }
        Systemlog systemlog = new Systemlog(adminId, LocalDateTime.now(), "管理员添加图书失败");
        systemlogService.save(systemlog);
        return Result.fail("添加图书失败！");

    }



    @DeleteMapping("/{id}/{adminId}")
    public Result delete(@PathVariable("id") Integer id,@PathVariable("adminId") Integer adminId){
        Assert.notNull(adminId,"权限不够！");
        boolean result = service.removeById(id);
        Assert.isTrue(result,"删除失败！");
        Systemlog systemlog = new Systemlog(adminId, LocalDateTime.now(), "管理员删除图书成功");
        systemlogService.save(systemlog);
        return Result.succ(result);
    }

    @PutMapping("/update/{adminId}")
    public Result update(@RequestBody Book book,@PathVariable("adminId") Integer adminId){
        Assert.notNull(adminId,"权限不够！");
        if(book.getCoverImage()==""){
            book.setCoverImage(null);
        }else{
            book.setCoverImage("/static/"+book.getCoverImage());
        }
        if(book.getBookId()!=null && book.getCategoryId()!=null && book.getAuthor()!=""
                && book.getDescription()!="" && book.getPublisher()!=""
                && book.getRating()!=null && book.getReleaseDate()!=null ){

            boolean result = service.updateById(book);
            Assert.notNull(result,"修改失败！");
            Systemlog systemlog = new Systemlog(adminId, LocalDateTime.now(), "管理员修改图书成功");
            systemlogService.save(systemlog);
            return Result.succ(result);
        }
        Systemlog systemlog = new Systemlog(adminId, LocalDateTime.now(), "管理员添加图书失败");
        systemlogService.save(systemlog);
        return Result.fail("修改图书失败！");
    }




}

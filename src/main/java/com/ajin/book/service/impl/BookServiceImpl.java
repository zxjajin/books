package com.ajin.book.service.impl;

import com.ajin.book.dto.BookDto;
import com.ajin.book.entity.Book;
import com.ajin.book.entity.Category;
import com.ajin.book.mapper.BookMapper;
import com.ajin.book.service.BookService;
import com.ajin.book.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 书籍表 服务实现类
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public BookDto getBookDto(Integer id) {
        Book book = this.getById(id);
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book,bookDto);
        Category category = categoryService.getById(book.getCategoryId());
        bookDto.setCategoryName(category.getCategoryName());
        List<Category> list = categoryService.list();
        bookDto.setCategoryList(new ArrayList<Category>());
        bookDto.getCategoryList().addAll(list);
        return bookDto;
    }
}

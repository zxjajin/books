package com.ajin.book.service.impl;

import com.ajin.book.entity.Book;
import com.ajin.book.mapper.BookMapper;
import com.ajin.book.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

package com.ajin.book.mapper;

import com.ajin.book.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 书籍表 Mapper 接口
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
public interface BookMapper extends BaseMapper<Book> {

    @Select("SELECT b.book_id, b.title, b.author, b.price, c.category_name" +
            "FROM book b JOIN category c ON b.category_id = c.category_id")
    List<Book> selectBooks();

}

package com.ajin.book.dto;

import com.ajin.book.entity.Book;
import com.ajin.book.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author ajin
 * @create 2023-10-14 3:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto extends Book {
    /**
     * 类别名称
     */
    private String categoryName;

    private List<Category> categoryList;

}

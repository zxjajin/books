package com.ajin.book.dto;

import com.ajin.book.entity.Readinghistory;
import lombok.Data;

/**
 * @author ajin
 * @create 2023-10-17 20:46
 */
@Data
public class ReadinghistoryDto extends Readinghistory {

    /**
     * 书名
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    private String coverImage;
}

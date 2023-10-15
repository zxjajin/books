package com.ajin.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Readinghistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 图书ID
     */
    private Integer bookId;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

    public Readinghistory(Integer userId, Integer bookId, LocalDateTime readTime) {
        this.userId = userId;
        this.bookId = bookId;
        this.readTime = readTime;
    }
}

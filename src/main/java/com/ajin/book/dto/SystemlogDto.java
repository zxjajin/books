package com.ajin.book.dto;

import com.ajin.book.entity.Systemlog;
import lombok.Data;

/**
 * @author ajin
 * @create 2023-10-15 17:40
 */
@Data
public class SystemlogDto extends Systemlog {
    private String username;
}

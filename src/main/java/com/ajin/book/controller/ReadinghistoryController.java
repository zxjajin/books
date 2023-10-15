package com.ajin.book.controller;


import com.ajin.book.service.impl.ReadinghistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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


}

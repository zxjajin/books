package com.ajin.book.service.impl;

import com.ajin.book.entity.Category;
import com.ajin.book.mapper.CategoryMapper;
import com.ajin.book.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类别表 服务实现类
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}

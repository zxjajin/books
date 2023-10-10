package com.ajin.book.service.impl;

import com.ajin.book.entity.Admin;
import com.ajin.book.mapper.AdminMapper;
import com.ajin.book.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}

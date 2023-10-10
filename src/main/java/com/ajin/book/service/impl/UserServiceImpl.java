package com.ajin.book.service.impl;

import com.ajin.book.entity.User;
import com.ajin.book.mapper.UserMapper;
import com.ajin.book.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

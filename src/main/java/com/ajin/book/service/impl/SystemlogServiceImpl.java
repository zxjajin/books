package com.ajin.book.service.impl;

import com.ajin.book.entity.Systemlog;
import com.ajin.book.mapper.SystemlogMapper;
import com.ajin.book.service.SystemlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author AJin
 * @since 2023-10-09
 */
@Service
public class SystemlogServiceImpl extends ServiceImpl<SystemlogMapper, Systemlog> implements SystemlogService {

}

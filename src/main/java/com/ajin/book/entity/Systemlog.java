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
 * 系统日志表
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
public class Systemlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 操作内容
     */
    private String operationContent;

    /**
     * 异常信息
     */
    private String exceptionInfo;

    public Systemlog(Integer userId, LocalDateTime operationTime, String operationContent, String exceptionInfo) {
        this.userId = userId;
        this.operationTime = operationTime;
        this.operationContent = operationContent;
        this.exceptionInfo = exceptionInfo;
    }

    public Systemlog(Integer adminId, LocalDateTime operationTime, String operationContent) {
        this.adminId = adminId;
        this.operationTime = operationTime;
        this.operationContent = operationContent;
    }
}

package com.ruoyi.project.tool.email.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class SysEmailConfig extends BaseEntity {

    private Long id;

    /**
     * 邮件服务器SMTP地址
     */
    private String host;

    /**
     * 邮件服务器 SMTP 端口
     */
    private String port;

    /**
     * 发件人邮箱
     */
    private String fromUser;

    /**
     * 发件者用户名，默认为发件人邮箱前缀
     */
    private String username;

    /**
     * 发件人邮箱授权码
     */
    private String password;

}

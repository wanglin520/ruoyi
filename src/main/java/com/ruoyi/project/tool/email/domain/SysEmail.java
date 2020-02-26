package com.ruoyi.project.tool.email.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 邮件列表
 * @author wangyg
 * @date 2018/09/28 12:02:14
 */
@Data
public class SysEmail extends BaseEntity {

    /** 自增主键 */
    private Long id;

    /** 收件人邮箱*/
    @Excel(name = "收件人邮箱" )
    private String toEmail;

    /** 收件人姓名*/
    @Excel(name = "收件人姓名" )
    private String toName;

    /** 发件人 */
    @Excel(name = "发件人" )
    private String fromEmail;

    /** 标题 */
    @Excel(name = "标题" )
    private String subject;

    /** 邮件类型: 收件1, 发件2, 草稿3, 垃圾4 */
    @Excel(name = "邮件类型: 收件1, 发件2, 草稿3, 垃圾4" )
    private Integer emailType;

    /** 发送内容 */
    @Excel(name = "发送内容" )
    private String content;

    /** 发送时间 */
    @Excel(name = "发送时间" , width = 30, dateFormat = "yyyy-MM-dd" )
    private Date time;

    /** 收件人邮箱*/
    List<String> tos;

    /** 收件人用户名*/
    List<String> names;
}

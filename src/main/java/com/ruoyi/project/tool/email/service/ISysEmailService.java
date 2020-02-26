package com.ruoyi.project.tool.email.service;

import com.ruoyi.project.tool.email.domain.SysEmail;
import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

public interface ISysEmailService {

    /**
     * 查询各邮件数量
     * @return
     */
    Map<String, Object> count();

    /**
     * 查询邮件列表
     *
     * @param sysEmail 邮件
     * @return 邮件集合
     */
    public List<SysEmail> selectSysEmailList(SysEmail sysEmail);

    /**
     * 新增邮件
     *
     * @param sysEmail 邮件
     * @return 结果
     */
    public int insertSysEmail(SysEmail sysEmail);

    /**
     * 删除邮件信息
     *
     * @param id 邮件ID
     * @return 结果
     */
    public int deleteSysEmailById(Long id);

    /**
     * 彻底删除邮件
     * @param id
     * @return
     */
    int compDeleteSysEmailById(Long id);

    /**
     * 恢复邮件
     * @param id
     * @return
     */
    int recoverSysEmailById(Long id);

    /**
     * 发送邮件
     * @param sysEmail 邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    // @Async
    void send(SysEmail sysEmail, SysEmailConfig emailConfig) throws Exception;

    /**
     * 存为草稿
     * @param sysEmail
     * @param sysEmailConfig
     * @return
     */
    int saveDraftEmail(SysEmail sysEmail, SysEmailConfig sysEmailConfig);
}

package com.ruoyi.project.tool.email.mapper;

import com.ruoyi.project.tool.email.domain.SysEmail;
import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import com.ruoyi.project.tool.job.domain.SysJob;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysEmailMapper {

    /**
     * 查询收件箱和发件箱数量
     * @param fMap
     * @return
     */
    Integer queryCount(Map fMap);

    /**
     * 查询邮件列表
     *
     * @param paramMap 查询条件
     * @return 邮件集合
     */
    public List<SysEmail> selectSysEmailList(Map paramMap);

    /**
     * 新增邮件
     *
     * @param sysEmail 邮件
     * @return 结果
     */
    public int insertSysEmail(SysEmail sysEmail);

    /**
     * 查询邮件详情
     *
     * @param id 邮件ID
     * @return 邮件
     */
    public SysEmail selectSysEmailById(Long id);

    /**
     * 修改邮件
     *
     * @param sysEmail 邮件
     * @return 结果
     */
    public int updateSysEmail(SysEmail sysEmail);

    /**
     * 删除邮件
     *
     * @param id 邮件ID
     * @return 结果
     */
    public int deleteSysEmailById(Long id);

}

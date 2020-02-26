package com.ruoyi.project.tool.job.service;

import com.ruoyi.project.tool.job.domain.SysJobLog;

import java.util.List;

/**
 * 定时任务日志Service接口
 *
 * @author ruoyi
 * @date 2020-02-13
 */
public interface ISysJobLogService {
    /**
     * 查询定时任务日志
     *
     * @param id 定时任务日志ID
     * @return 定时任务日志
     */
    public SysJobLog selectSysJobLogById(Long id);

    /**
     * 查询定时任务日志列表
     *
     * @param sysJobLog 定时任务日志
     * @return 定时任务日志集合
     */
    public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog);

    /**
     * 新增定时任务日志
     *
     * @param sysJobLog 定时任务日志
     * @return 结果
     */
    public int insertSysJobLog(SysJobLog sysJobLog);

    /**
     * 修改定时任务日志
     *
     * @param sysJobLog 定时任务日志
     * @return 结果
     */
    public int updateSysJobLog(SysJobLog sysJobLog);

    /**
     * 批量删除定时任务日志
     *
     * @param ids 需要删除的定时任务日志ID
     * @return 结果
     */
    public int deleteSysJobLogByIds(Long[] ids);

    /**
     * 删除定时任务日志信息
     *
     * @param id 定时任务日志ID
     * @return 结果
     */
    public int deleteSysJobLogById(Long id);

    /**
     * 清空定时任务日志信息
     */
    void cleanJobLog();
}

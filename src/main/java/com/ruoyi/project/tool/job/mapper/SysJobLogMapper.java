package com.ruoyi.project.tool.job.mapper;


import com.ruoyi.project.tool.job.domain.SysJobLog;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 定时任务日志Mapper接口
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@Repository
public interface SysJobLogMapper {
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
     * 删除定时任务日志
     *
     * @param id 定时任务日志ID
     * @return 结果
     */
    public int deleteSysJobLogById(Long id);

    /**
     * 批量删除定时任务日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJobLogByIds(Long[] ids);

    /**
     * 清空定时任务日志信息
     */
    void cleanJobLog();
}

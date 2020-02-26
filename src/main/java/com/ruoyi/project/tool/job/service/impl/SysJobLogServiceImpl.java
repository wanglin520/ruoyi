package com.ruoyi.project.tool.job.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.tool.job.domain.SysJobLog;
import com.ruoyi.project.tool.job.mapper.SysJobLogMapper;
import com.ruoyi.project.tool.job.service.ISysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务日志Service业务层处理
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService {
    @Autowired
    private SysJobLogMapper sysJobLogMapper;

    /**
     * 查询定时任务日志
     *
     * @param id 定时任务日志ID
     * @return 定时任务日志
     */
    @Override
    public SysJobLog selectSysJobLogById(Long id) {
        return sysJobLogMapper.selectSysJobLogById(id);
    }

    /**
     * 查询定时任务日志列表
     *
     * @param sysJobLog 定时任务日志
     * @return 定时任务日志
     */
    @Override
    public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog) {
        return sysJobLogMapper.selectSysJobLogList(sysJobLog);
    }

    /**
     * 新增定时任务日志
     *
     * @param sysJobLog 定时任务日志
     * @return 结果
     */
    @Override
    public int insertSysJobLog(SysJobLog sysJobLog) {
        sysJobLog.setCreateTime(DateUtils.getNowDate());
        return sysJobLogMapper.insertSysJobLog(sysJobLog);
    }

    /**
     * 修改定时任务日志
     *
     * @param sysJobLog 定时任务日志
     * @return 结果
     */
    @Override
    public int updateSysJobLog(SysJobLog sysJobLog) {
        return sysJobLogMapper.updateSysJobLog(sysJobLog);
    }

    /**
     * 批量删除定时任务日志
     *
     * @param ids 需要删除的定时任务日志ID
     * @return 结果
     */
    @Override
    public int deleteSysJobLogByIds(Long[] ids) {
        return sysJobLogMapper.deleteSysJobLogByIds(ids);
    }

    /**
     * 删除定时任务日志信息
     *
     * @param id 定时任务日志ID
     * @return 结果
     */
    @Override
    public int deleteSysJobLogById(Long id) {
        return sysJobLogMapper.deleteSysJobLogById(id);
    }

    /**
     * 清空定时任务日志信息
     */
    @Override
    public void cleanJobLog() {
        sysJobLogMapper.cleanJobLog();
    }
}

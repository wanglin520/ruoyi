package com.ruoyi.project.tool.job.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.quartz.QuartzManage;
import com.ruoyi.project.tool.job.domain.SysJob;
import com.ruoyi.project.tool.job.mapper.SysJobMapper;
import com.ruoyi.project.tool.job.service.ISysJobService;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务Service业务层处理
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@Service
public class SysJobServiceImpl implements ISysJobService {
    @Autowired
    private SysJobMapper sysJobMapper;

    @Autowired
    private QuartzManage quartzManage;

    /**
     * 查询定时任务
     *
     * @param id 定时任务ID
     * @return 定时任务
     */
    @Override
    public SysJob selectSysJobById(Long id) {
        return sysJobMapper.selectSysJobById(id);
    }

    /**
     * 查询定时任务列表
     *
     * @param sysJob 定时任务
     * @return 定时任务
     */
    @Override
    public List<SysJob> selectSysJobList(SysJob sysJob) {
        return sysJobMapper.selectSysJobList(sysJob);
    }

    /**
     * 新增定时任务
     *
     * @param sysJob 定时任务
     * @return 结果
     */
    @Override
    public int insertSysJob(SysJob sysJob) {
        sysJob.setCreateTime(DateUtils.getNowDate());
        sysJob.setCreateBy(SecurityUtils.getUsername());

        int insertNum = sysJobMapper.insertSysJob(sysJob);
        quartzManage.addJob(sysJob);
        return insertNum;
    }

    /**
     * 修改定时任务
     *
     * @param sysJob 定时任务
     * @return 结果
     */
    @Override
    public int updateSysJob(SysJob sysJob) {
        sysJob.setUpdateBy(SecurityUtils.getUsername());
        sysJob.setUpdateTime(DateUtils.getNowDate());
        quartzManage.updateJobCron(sysJob);
        return sysJobMapper.updateSysJob(sysJob);
    }

    /**
     * 批量删除定时任务
     *
     * @param sysJobList 需要删除的定时任务
     * @return 结果
     */
    @Override
    public void deleteSysJobByIds(List<SysJob> sysJobList) {
        for (SysJob sysJob : sysJobList) {
            quartzManage.deleteJob(sysJob);
            sysJobMapper.deleteSysJobById(sysJob.getId());
        }
    }

    /**
     * 删除定时任务信息
     *
     * @param id 定时任务ID
     * @return 结果
     */
    @Override
    public int deleteSysJobById(Long id) {
        return sysJobMapper.deleteSysJobById(id);
    }

    /**
     * 执行定时任务
     *
     * @param sysJob
     * @return
     */
    @Override
    public void runJobById(SysJob sysJob) {
        quartzManage.runJobNow(sysJob);
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression
     * @return
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        if (!CronExpression.isValidExpression(cronExpression)){
           return false;
        } else {
            return true;
        }
    }

    /**
     * 更改定时任务状态
     *
     * @param quartzJob /
     */
    @Override
    public int updateIsPause(SysJob quartzJob) {
        // 暂停状态
        if (quartzJob.getIsPause().equals("1")) {
            // 恢复
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause("1");
        } else {
            // 暂停
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause("0");
        }
        return sysJobMapper.updateSysJob(quartzJob);
    }
}

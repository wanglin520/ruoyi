package com.ruoyi.project.tool.job.service;

import com.ruoyi.project.tool.job.domain.SysJob;
import java.util.List;

/**
 * 定时任务Service接口
 *
 * @author ruoyi
 * @date 2020-02-13
 */
public interface ISysJobService {
    /**
     * 查询定时任务
     *
     * @param id 定时任务ID
     * @return 定时任务
     */
    public SysJob selectSysJobById(Long id);

    /**
     * 查询定时任务列表
     *
     * @param sysJob 定时任务
     * @return 定时任务集合
     */
    public List<SysJob> selectSysJobList(SysJob sysJob);

    /**
     * 新增定时任务
     *
     * @param sysJob 定时任务
     * @return 结果
     */
    public int insertSysJob(SysJob sysJob);

    /**
     * 修改定时任务
     *
     * @param sysJob 定时任务
     * @return 结果
     */
    public int updateSysJob(SysJob sysJob);

    /**
     * 批量删除定时任务
     *
     * @param sysJobList 需要删除的定时任务集合
     * @return 结果
     */
    public void deleteSysJobByIds(List<SysJob> sysJobList);

    /**
     * 删除定时任务信息
     *
     * @param id 定时任务ID
     * @return 结果
     */
    public int deleteSysJobById(Long id);

    /**
     * 执行定时任务
     * @param sysJob
     * @return
     */
    void runJobById(SysJob sysJob);

    /**
     * 校验cron表达式是否有效
     * @param cronExpression
     * @return
     */
    boolean checkCronExpressionIsValid(String cronExpression);

    /**
     * 更改定时任务状态
     * @param quartzJob /
     */
    int updateIsPause(SysJob quartzJob);
}

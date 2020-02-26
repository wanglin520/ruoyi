package com.ruoyi.project.tool.job.mapper;

import com.ruoyi.project.tool.job.domain.SysJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务Mapper接口
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@Repository
public interface SysJobMapper {
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
     * 删除定时任务
     *
     * @param id 定时任务ID
     * @return 结果
     */
    public int deleteSysJobById(Long id);

    /**
     * 批量删除定时任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJobByIds(Long[] ids);

    /**
     * 查询启用的任务
     * @return List
     */
    List<SysJob> findByIsPauseIsFalse();
}

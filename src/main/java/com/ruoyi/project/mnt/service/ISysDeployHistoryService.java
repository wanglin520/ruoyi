package com.ruoyi.project.mnt.service;

import com.ruoyi.project.mnt.domain.SysDeployHistory;
import java.util.List;

/**
 * 部署历史管理Service接口
 *
 * @author wangyg
 * @date 2020-02-17
 */
public interface ISysDeployHistoryService {
    /**
     * 查询部署历史管理
     *
     * @param id 部署历史管理ID
     * @return 部署历史管理
     */
    public SysDeployHistory selectSysDeployHistoryById(String id);

    /**
     * 查询部署历史管理列表
     *
     * @param sysDeployHistory 部署历史管理
     * @return 部署历史管理集合
     */
    public List<SysDeployHistory> selectSysDeployHistoryList(SysDeployHistory sysDeployHistory);

    /**
     * 新增部署历史管理
     *
     * @param sysDeployHistory 部署历史管理
     * @return 结果
     */
    public int insertSysDeployHistory(SysDeployHistory sysDeployHistory);

    /**
     * 修改部署历史管理
     *
     * @param sysDeployHistory 部署历史管理
     * @return 结果
     */
    public int updateSysDeployHistory(SysDeployHistory sysDeployHistory);

    /**
     * 批量删除部署历史管理
     *
     * @param ids 需要删除的部署历史管理ID
     * @return 结果
     */
    public int deleteSysDeployHistoryByIds(String[] ids);

    /**
     * 删除部署历史管理信息
     *
     * @param id 部署历史管理ID
     * @return 结果
     */
    public int deleteSysDeployHistoryById(String id);
}

package com.ruoyi.project.mnt.mapper;

import com.ruoyi.project.mnt.domain.SysDeployHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部署历史管理Mapper接口
 *
 * @author wangyg
 * @date 2020-02-17
 */
@Repository
public interface SysDeployHistoryMapper {
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
     * 删除部署历史管理
     *
     * @param id 部署历史管理ID
     * @return 结果
     */
    public int deleteSysDeployHistoryById(String id);

    /**
     * 批量删除部署历史管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDeployHistoryByIds(String[] ids);
}

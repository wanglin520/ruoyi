package com.ruoyi.project.mnt.service.impl;

import java.util.List;
                                                                            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mnt.mapper.SysDeployHistoryMapper;
import com.ruoyi.project.mnt.domain.SysDeployHistory;
import com.ruoyi.project.mnt.service.ISysDeployHistoryService;

/**
 * 部署历史管理Service业务层处理
 *
 * @author wangyg
 * @date 2020-02-17
 */
@Service
public class SysDeployHistoryServiceImpl implements ISysDeployHistoryService {
    @Autowired
    private SysDeployHistoryMapper sysDeployHistoryMapper;

    /**
     * 查询部署历史管理
     *
     * @param id 部署历史管理ID
     * @return 部署历史管理
     */
    @Override
    public SysDeployHistory selectSysDeployHistoryById(String id) {
        return sysDeployHistoryMapper.selectSysDeployHistoryById(id);
    }

    /**
     * 查询部署历史管理列表
     *
     * @param sysDeployHistory 部署历史管理
     * @return 部署历史管理
     */
    @Override
    public List<SysDeployHistory> selectSysDeployHistoryList(SysDeployHistory sysDeployHistory) {
        return sysDeployHistoryMapper.selectSysDeployHistoryList(sysDeployHistory);
    }

    /**
     * 新增部署历史管理
     *
     * @param sysDeployHistory 部署历史管理
     * @return 结果
     */
    @Override
    public int insertSysDeployHistory(SysDeployHistory sysDeployHistory) {
                                                                                                                                        return sysDeployHistoryMapper.insertSysDeployHistory(sysDeployHistory);
    }

    /**
     * 修改部署历史管理
     *
     * @param sysDeployHistory 部署历史管理
     * @return 结果
     */
    @Override
    public int updateSysDeployHistory(SysDeployHistory sysDeployHistory) {
                                                                                                                                        return sysDeployHistoryMapper.updateSysDeployHistory(sysDeployHistory);
    }

    /**
     * 批量删除部署历史管理
     *
     * @param ids 需要删除的部署历史管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDeployHistoryByIds(String[] ids) {
        return sysDeployHistoryMapper.deleteSysDeployHistoryByIds(ids);
    }

    /**
     * 删除部署历史管理信息
     *
     * @param id 部署历史管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDeployHistoryById(String id) {
        return sysDeployHistoryMapper.deleteSysDeployHistoryById(id);
    }
}

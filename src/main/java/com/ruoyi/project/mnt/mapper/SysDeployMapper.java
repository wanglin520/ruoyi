package com.ruoyi.project.mnt.mapper;

import com.ruoyi.project.mnt.domain.SysDeploy;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部署管理Mapper接口
 *
 * @author wangyg
 * @date 2020-02-17
 */
@Repository
public interface SysDeployMapper {
    /**
     * 查询部署管理
     *
     * @param id 部署管理ID
     * @return 部署管理
     */
    public SysDeploy selectSysDeployById(Long id);

    /**
     * 查询部署管理列表
     *
     * @param sysDeploy 部署管理
     * @return 部署管理集合
     */
    public List<SysDeploy> selectSysDeployList(SysDeploy sysDeploy);

    /**
     * 新增部署管理
     *
     * @param sysDeploy 部署管理
     * @return 结果
     */
    public int insertSysDeploy(SysDeploy sysDeploy);

    /**
     * 修改部署管理
     *
     * @param sysDeploy 部署管理
     * @return 结果
     */
    public int updateSysDeploy(SysDeploy sysDeploy);

    /**
     * 删除部署管理
     *
     * @param id 部署管理ID
     * @return 结果
     */
    public int deleteSysDeployById(Long id);

    /**
     * 批量删除部署管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDeployByIds(Long[] ids);
}

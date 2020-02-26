package com.ruoyi.project.mnt.service;

import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.mnt.domain.SysDeploy;
import com.ruoyi.project.mnt.domain.SysDeployHistory;

import java.util.List;

/**
 * 部署管理Service接口
 *
 * @author wangyg
 * @date 2020-02-17
 */
public interface ISysDeployService {
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
     * 批量删除部署管理
     *
     * @param ids 需要删除的部署管理ID
     * @return 结果
     */
    public int deleteSysDeployByIds(Long[] ids);

    /**
     * 删除部署管理信息
     *
     * @param id 部署管理ID
     * @return 结果
     */
    public AjaxResult deleteSysDeployById(Long id);

    /**
     * 启动服务
     * @param sysDeploy
     */
    AjaxResult startDeployServer(SysDeploy sysDeploy);

    /**
     * 停止服务
     * @param sysDeploy
     * @return
     */
    AjaxResult stopDeployServer(SysDeploy sysDeploy);

    /**
     * 一键部署
     * @param fileSavePath 文件路径
     * @param id 应用ID
     */
    AjaxResult deploy(String fileSavePath, Long id);

    /**
     * 系统还原
     * @param sysDeployHistory 需要还原的备份
     * @return
     */
    AjaxResult restore(SysDeployHistory sysDeployHistory);
}

package com.ruoyi.project.mnt.service;

import com.ruoyi.project.mnt.domain.SysServer;
import java.util.List;

/**
 * 服务器管理Service接口
 *
 * @author wangyg
 * @date 2020-02-14
 */
public interface ISysServerService {
    /**
     * 查询服务器管理
     *
     * @param id 服务器管理ID
     * @return 服务器管理
     */
    public SysServer selectSysServerById(Long id);

    /**
     * 查询服务器管理列表
     *
     * @param sysServer 服务器管理
     * @return 服务器管理集合
     */
    public List<SysServer> selectSysServerList(SysServer sysServer);

    /**
     * 新增服务器管理
     *
     * @param sysServer 服务器管理
     * @return 结果
     */
    public int insertSysServer(SysServer sysServer);

    /**
     * 修改服务器管理
     *
     * @param sysServer 服务器管理
     * @return 结果
     */
    public int updateSysServer(SysServer sysServer);

    /**
     * 批量删除服务器管理
     *
     * @param ids 需要删除的服务器管理ID
     * @return 结果
     */
    public int deleteSysServerByIds(Long[] ids);

    /**
     * 删除服务器管理信息
     *
     * @param id 服务器管理ID
     * @return 结果
     */
    public int deleteSysServerById(Long id);

    /**
     * 测试登录服务器
     * @param sysServer
     * @return
     */
    Boolean testConnect(SysServer sysServer);

    /**
     * 查询服务器下拉选
     * @return
     */
    List<SysServer> queryServerSelect();
}

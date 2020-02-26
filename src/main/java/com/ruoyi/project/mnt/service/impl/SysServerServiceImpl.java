package com.ruoyi.project.mnt.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.shell.ExecuteShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mnt.mapper.SysServerMapper;
import com.ruoyi.project.mnt.domain.SysServer;
import com.ruoyi.project.mnt.service.ISysServerService;

/**
 * 服务器管理Service业务层处理
 *
 * @author wangyg
 * @date 2020-02-14
 */
@Service
public class SysServerServiceImpl implements ISysServerService {
    @Autowired
    private SysServerMapper sysServerMapper;

    /**
     * 查询服务器管理
     *
     * @param id 服务器管理ID
     * @return 服务器管理
     */
    @Override
    public SysServer selectSysServerById(Long id) {
        return sysServerMapper.selectSysServerById(id);
    }

    /**
     * 查询服务器管理列表
     *
     * @param sysServer 服务器管理
     * @return 服务器管理
     */
    @Override
    public List<SysServer> selectSysServerList(SysServer sysServer) {
        return sysServerMapper.selectSysServerList(sysServer);
    }

    /**
     * 新增服务器管理
     *
     * @param sysServer 服务器管理
     * @return 结果
     */
    @Override
    public int insertSysServer(SysServer sysServer) {
        sysServer.setCreateTime(DateUtils.getNowDate());
        return sysServerMapper.insertSysServer(sysServer);
    }

    /**
     * 修改服务器管理
     *
     * @param sysServer 服务器管理
     * @return 结果
     */
    @Override
    public int updateSysServer(SysServer sysServer) {
        return sysServerMapper.updateSysServer(sysServer);
    }

    /**
     * 批量删除服务器管理
     *
     * @param ids 需要删除的服务器管理ID
     * @return 结果
     */
    @Override
    public int deleteSysServerByIds(Long[] ids) {
        return sysServerMapper.deleteSysServerByIds(ids);
    }

    /**
     * 删除服务器管理信息
     *
     * @param id 服务器管理ID
     * @return 结果
     */
    @Override
    public int deleteSysServerById(Long id) {
        return sysServerMapper.deleteSysServerById(id);
    }

    /**
     * 测试登录服务器
     *
     * @param sysServer
     * @return
     */
    @Override
    public Boolean testConnect(SysServer sysServer) {
        ExecuteShellUtil executeShellUtil = null;
        try {
            executeShellUtil = new ExecuteShellUtil(sysServer.getIp(), sysServer.getAccount(), sysServer.getPassword(), sysServer.getPort());
            return executeShellUtil.execute("ls") == 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (executeShellUtil != null) {
                executeShellUtil.close();
            }
        }
    }

    /**
     * 查询服务器下拉选
     *
     * @return
     */
    @Override
    public List<SysServer> queryServerSelect() {
        return sysServerMapper.queryServerSelect();
    }
}

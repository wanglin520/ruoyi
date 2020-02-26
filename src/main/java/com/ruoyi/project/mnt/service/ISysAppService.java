package com.ruoyi.project.mnt.service;

import com.ruoyi.project.mnt.domain.SysApp;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 应用管理Service接口
 *
 * @author wangyg
 * @date 2020-02-17
 */
public interface ISysAppService {
    /**
     * 查询应用管理
     *
     * @param id 应用管理ID
     * @return 应用管理
     */
    public SysApp selectSysAppById(Long id);

    /**
     * 查询应用管理列表
     *
     * @param sysApp 应用管理
     * @return 应用管理集合
     */
    public List<SysApp> selectSysAppList(SysApp sysApp);

    /**
     * 新增应用管理
     *
     * @param sysApp 应用管理
     * @return 结果
     */
    public int insertSysApp(SysApp sysApp) throws UnsupportedEncodingException;

    /**
     * 修改应用管理
     *
     * @param sysApp 应用管理
     * @return 结果
     */
    public int updateSysApp(SysApp sysApp) throws UnsupportedEncodingException;

    /**
     * 批量删除应用管理
     *
     * @param ids 需要删除的应用管理ID
     * @return 结果
     */
    public int deleteSysAppByIds(Long[] ids);

    /**
     * 删除应用管理信息
     *
     * @param id 应用管理ID
     * @return 结果
     */
    public int deleteSysAppById(Long id);

    /**
     * 查询应用下拉选
     * @return
     */
    List<SysApp> queryAppSelect();
}

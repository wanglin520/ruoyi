package com.ruoyi.project.mnt.mapper;

import com.ruoyi.project.mnt.domain.SysApp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用管理Mapper接口
 *
 * @author wangyg
 * @date 2020-02-17
 */
@Repository
public interface SysAppMapper {
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
    public int insertSysApp(SysApp sysApp);

    /**
     * 修改应用管理
     *
     * @param sysApp 应用管理
     * @return 结果
     */
    public int updateSysApp(SysApp sysApp);

    /**
     * 删除应用管理
     *
     * @param id 应用管理ID
     * @return 结果
     */
    public int deleteSysAppById(Long id);

    /**
     * 批量删除应用管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAppByIds(Long[] ids);

    /**
     * 查询应用下拉选
     * @return
     */
    List<SysApp> queryAppSelect();
}

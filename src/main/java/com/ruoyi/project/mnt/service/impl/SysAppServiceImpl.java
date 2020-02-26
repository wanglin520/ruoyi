package com.ruoyi.project.mnt.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mnt.mapper.SysAppMapper;
import com.ruoyi.project.mnt.domain.SysApp;
import com.ruoyi.project.mnt.service.ISysAppService;

/**
 * 应用管理Service业务层处理
 *
 * @author wangyg
 * @date 2020-02-17
 */
@Service
public class SysAppServiceImpl implements ISysAppService {
    @Autowired
    private SysAppMapper sysAppMapper;

    /**
     * 查询应用管理
     *
     * @param id 应用管理ID
     * @return 应用管理
     */
    @Override
    public SysApp selectSysAppById(Long id) {
        return sysAppMapper.selectSysAppById(id);
    }

    /**
     * 查询应用管理列表
     *
     * @param sysApp 应用管理
     * @return 应用管理
     */
    @Override
    public List<SysApp> selectSysAppList(SysApp sysApp) {
        return sysAppMapper.selectSysAppList(sysApp);
    }

    /**
     * 新增应用管理
     *
     * @param sysApp 应用管理
     * @return 结果
     */
    @Override
    public int insertSysApp(SysApp sysApp) throws UnsupportedEncodingException {
        sysApp.setCreateTime(DateUtils.getNowDate());
        String startScript = URLDecoder.decode(sysApp.getStartScript(),"UTF-8");
        String deployScript = URLDecoder.decode(sysApp.getDeployScript(),"UTF-8");
        sysApp.setStartScript(startScript);
        sysApp.setDeployScript(deployScript);
        return sysAppMapper.insertSysApp(sysApp);
    }

    /**
     * 修改应用管理
     *
     * @param sysApp 应用管理
     * @return 结果
     */
    @Override
    public int updateSysApp(SysApp sysApp) {
        try {
            String startScript = URLDecoder.decode(sysApp.getStartScript(),"UTF-8");
            String deployScript = URLDecoder.decode(sysApp.getDeployScript(),"UTF-8");
            sysApp.setStartScript(startScript);
            sysApp.setDeployScript(deployScript);
            return sysAppMapper.updateSysApp(sysApp);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 批量删除应用管理
     *
     * @param ids 需要删除的应用管理ID
     * @return 结果
     */
    @Override
    public int deleteSysAppByIds(Long[] ids) {
        return sysAppMapper.deleteSysAppByIds(ids);
    }

    /**
     * 删除应用管理信息
     *
     * @param id 应用管理ID
     * @return 结果
     */
    @Override
    public int deleteSysAppById(Long id) {
        return sysAppMapper.deleteSysAppById(id);
    }

    /**
     * 查询应用下拉选
     *
     * @return
     */
    @Override
    public List<SysApp> queryAppSelect() {
        return sysAppMapper.queryAppSelect();
    }
}

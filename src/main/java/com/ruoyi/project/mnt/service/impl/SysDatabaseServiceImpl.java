package com.ruoyi.project.mnt.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sql.SqlUtil;
import com.ruoyi.project.mnt.domain.SysDatabase;
import com.ruoyi.project.mnt.mapper.SysDatabaseMapper;
import com.ruoyi.project.mnt.service.ISysDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据库管理Service业务层处理
 *
 * @author wangyg
 * @date 2020-02-15
 */
@Service
public class SysDatabaseServiceImpl implements ISysDatabaseService {
    @Autowired
    private SysDatabaseMapper sysDatabaseMapper;

    /**
     * 查询数据库管理
     *
     * @param id 数据库管理ID
     * @return 数据库管理
     */
    @Override
    public SysDatabase selectSysDatabaseById(String id) {
        return sysDatabaseMapper.selectSysDatabaseById(id);
    }

    /**
     * 查询数据库管理列表
     *
     * @param sysDatabase 数据库管理
     * @return 数据库管理
     */
    @Override
    public List<SysDatabase> selectSysDatabaseList(SysDatabase sysDatabase) {
        return sysDatabaseMapper.selectSysDatabaseList(sysDatabase);
    }

    /**
     * 新增数据库管理
     *
     * @param sysDatabase 数据库管理
     * @return 结果
     */
    @Override
    public int insertSysDatabase(SysDatabase sysDatabase) {
        sysDatabase.setCreateTime(DateUtils.getNowDate());
        return sysDatabaseMapper.insertSysDatabase(sysDatabase);
    }

    /**
     * 修改数据库管理
     *
     * @param sysDatabase 数据库管理
     * @return 结果
     */
    @Override
    public int updateSysDatabase(SysDatabase sysDatabase) {
        return sysDatabaseMapper.updateSysDatabase(sysDatabase);
    }

    /**
     * 批量删除数据库管理
     *
     * @param ids 需要删除的数据库管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDatabaseByIds(String[] ids) {
        return sysDatabaseMapper.deleteSysDatabaseByIds(ids);
    }

    /**
     * 删除数据库管理信息
     *
     * @param id 数据库管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDatabaseById(String id) {
        return sysDatabaseMapper.deleteSysDatabaseById(id);
    }

    /**
     * 测试数据库连接
     *
     * @param sysDatabase
     * @return
     */
    @Override
    public Boolean testConnect(SysDatabase sysDatabase) {
        try {
            return SqlUtil.testConnection(sysDatabase.getJdbcUrl(), sysDatabase.getUserName(), sysDatabase.getPwd());
        } catch (Exception e) {
            return false;
        }
    }
}

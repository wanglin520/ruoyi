package com.ruoyi.project.mnt.mapper;

import com.ruoyi.project.mnt.domain.SysDatabase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库管理Mapper接口
 *
 * @author wangyg
 * @date 2020-02-15
 */
@Repository
public interface SysDatabaseMapper {
    /**
     * 查询数据库管理
     *
     * @param id 数据库管理ID
     * @return 数据库管理
     */
    public SysDatabase selectSysDatabaseById(String id);

    /**
     * 查询数据库管理列表
     *
     * @param sysDatabase 数据库管理
     * @return 数据库管理集合
     */
    public List<SysDatabase> selectSysDatabaseList(SysDatabase sysDatabase);

    /**
     * 新增数据库管理
     *
     * @param sysDatabase 数据库管理
     * @return 结果
     */
    public int insertSysDatabase(SysDatabase sysDatabase);

    /**
     * 修改数据库管理
     *
     * @param sysDatabase 数据库管理
     * @return 结果
     */
    public int updateSysDatabase(SysDatabase sysDatabase);

    /**
     * 删除数据库管理
     *
     * @param id 数据库管理ID
     * @return 结果
     */
    public int deleteSysDatabaseById(String id);

    /**
     * 批量删除数据库管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDatabaseByIds(String[] ids);
}

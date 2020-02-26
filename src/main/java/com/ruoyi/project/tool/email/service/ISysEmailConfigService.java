package com.ruoyi.project.tool.email.service;

import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import java.util.List;

/**
 * 邮箱配置Service接口
 *
 * @author wangyg
 * @date 2020-02-24
 */
public interface ISysEmailConfigService {
    /**
     * 查询邮箱配置
     *
     * @param email 发件人邮箱
     * @return 邮箱配置
     */
    public SysEmailConfig selectSysEmailConfigByEmail(String email) throws Exception;

    /**
     * 查询邮箱配置列表
     *
     * @param sysEmailConfig 邮箱配置
     * @return 邮箱配置集合
     */
    public List<SysEmailConfig> selectSysEmailConfigList(SysEmailConfig sysEmailConfig);

    /**
     * 新增邮箱配置
     *
     * @param sysEmailConfig 邮箱配置
     * @return 结果
     */
    public int insertSysEmailConfig(SysEmailConfig sysEmailConfig);

    /**
     * 修改邮箱配置
     *
     * @param sysEmailConfig 邮箱配置
     * @return 结果
     */
    public int updateSysEmailConfig(SysEmailConfig sysEmailConfig);

}

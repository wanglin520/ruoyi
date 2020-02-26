package com.ruoyi.project.tool.email.service.impl;

import java.util.List;

import com.ruoyi.common.utils.EncryptUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import com.ruoyi.project.tool.email.mapper.SysEmailConfigMapper;
import com.ruoyi.project.tool.email.service.ISysEmailConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮箱配置Service业务层处理
 *
 * @author wangyg
 * @date 2020-02-24
 */
@Slf4j
@Service
public class SysEmailConfigServiceImpl implements ISysEmailConfigService {

    @Autowired
    private SysEmailConfigMapper sysEmailConfigMapper;

    /**
     * 查询邮箱配置
     *
     * @param email 邮箱
     * @return 邮箱配置
     */
    @Override
    public SysEmailConfig selectSysEmailConfigByEmail(String email) throws Exception {
        SysEmailConfig sysEmailConfig = sysEmailConfigMapper.selectSysEmailConfigByEmail(email);
        // 对称解密
        sysEmailConfig.setPassword(EncryptUtils.desDecrypt(sysEmailConfig.getPassword()));
        return sysEmailConfig;
    }

    /**
     * 查询邮箱配置列表
     *
     * @param sysEmailConfig 邮箱配置
     * @return 邮箱配置
     */
    @Override
    public List<SysEmailConfig> selectSysEmailConfigList(SysEmailConfig sysEmailConfig) {
        return sysEmailConfigMapper.selectSysEmailConfigList(sysEmailConfig);
    }

    /**
     * 新增邮箱配置
     *
     * @param sysEmailConfig 邮箱配置
     * @return 结果
     */
    @Override
    public int insertSysEmailConfig(SysEmailConfig sysEmailConfig) {
        try {
            // 对称加密
            sysEmailConfig.setPassword(EncryptUtils.desEncrypt(sysEmailConfig.getPassword()));
            return sysEmailConfigMapper.insertSysEmailConfig(sysEmailConfig);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return -1;
        }
    }

    /**
     * 修改邮箱配置
     *
     * @param sysEmailConfig 邮箱配置
     * @return 结果
     */
    @Override
    public int updateSysEmailConfig(SysEmailConfig sysEmailConfig) {
        try {
            // 对称加密
            sysEmailConfig.setPassword(EncryptUtils.desEncrypt(sysEmailConfig.getPassword()));
            return sysEmailConfigMapper.updateSysEmailConfig(sysEmailConfig);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return -1;
        }
    }
}

package com.ruoyi.project.tool.email.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import com.ruoyi.project.tool.email.service.ISysEmailConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 邮箱配置Controller
 *
 * @author wangyg
 * @date 2020-02-24
 */
@RestController
@RequestMapping("/tool/emailconfig")
public class SysEmailConfigController extends BaseController {

    @Autowired
    private ISysEmailConfigService sysEmailConfigService;

    /**
     * 查询邮箱配置列表
     */
    @PreAuthorize("@ss.hasPermi('tool:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEmailConfig sysEmailConfig) {
        startPage();
        List<SysEmailConfig> list = sysEmailConfigService.selectSysEmailConfigList(sysEmailConfig);
        return getDataTable(list);
    }

    /**
     * 获取邮箱配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('tool:config:query')")
    @GetMapping(value = "/getEmailConfig")
    public AjaxResult getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getUser();
        String email = sysUser.getEmail();
        try {
            return AjaxResult.success(sysEmailConfigService.selectSysEmailConfigByEmail(email));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 新增邮箱配置
     */
    @PreAuthorize("@ss.hasPermi('tool:config:add')")
    @Log(title = "新增邮箱配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEmailConfig sysEmailConfig) {
        return toAjax(sysEmailConfigService.insertSysEmailConfig(sysEmailConfig));
    }

    /**
     * 修改邮箱配置
     */
    @PreAuthorize("@ss.hasPermi('tool:config:edit')")
    @Log(title = "邮箱配置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateOrAddConfig")
    public AjaxResult edit(@RequestBody SysEmailConfig sysEmailConfig) {
        Integer num = 0;
        Long id = sysEmailConfig.getId();
        // 修改
        if(id != null) {
            num = sysEmailConfigService.updateSysEmailConfig(sysEmailConfig);
        } else {
            // 新增
            num = sysEmailConfigService.insertSysEmailConfig(sysEmailConfig);
        }
        return toAjax(num);
    }

}

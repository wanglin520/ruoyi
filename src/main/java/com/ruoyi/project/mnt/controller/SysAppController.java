package com.ruoyi.project.mnt.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.mnt.domain.SysApp;
import com.ruoyi.project.mnt.service.ISysAppService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 应用管理Controller
 *
 * @author wangyg
 * @date 2020-02-17
 */
@RestController
@RequestMapping("/mnt/app")
public class SysAppController extends BaseController {
    @Autowired
    private ISysAppService sysAppService;

    /**
     * 查询应用管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:app:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysApp sysApp) {
        startPage();
        List<SysApp> list = sysAppService.selectSysAppList(sysApp);
        return getDataTable(list);
    }

    /**
     * 导出应用管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:app:export')")
    @Log(title = "应用管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysApp sysApp) {
        List<SysApp> list = sysAppService.selectSysAppList(sysApp);
        ExcelUtil<SysApp> util = new ExcelUtil<SysApp>(SysApp. class);
        return util.exportExcel(list, "app");
    }

    /**
     * 获取应用管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mnt:app:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysAppService.selectSysAppById(id));
    }

    /**
     * 新增应用管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:app:add')")
    @Log(title = "应用管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysApp sysApp) {
        try {
            return toAjax(sysAppService.insertSysApp(sysApp));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
     * 修改应用管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:app:edit')")
    @Log(title = "应用管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysApp sysApp) {
        try {
            int num = sysAppService.updateSysApp(sysApp);
            return toAjax(num);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }

    /**
     * 删除应用管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:app:remove')")
    @Log(title = "应用管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysAppService.deleteSysAppByIds(ids));
    }

    /**
     * 查询应用下拉选
     */
    @GetMapping("/queryAppSelect")
    public AjaxResult queryAppSelect() {
        List<SysApp> list = sysAppService.queryAppSelect();
        return AjaxResult.success(list);
    }
}

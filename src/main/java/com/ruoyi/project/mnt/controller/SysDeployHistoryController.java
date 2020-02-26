package com.ruoyi.project.mnt.controller;

import java.util.List;

import com.ruoyi.project.mnt.service.ISysDeployHistoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.project.mnt.domain.SysDeployHistory;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 部署历史管理Controller
 *
 * @author wangyg
 * @date 2020-02-17
 */
@RestController
@RequestMapping("/mnt/deployHistory")
public class SysDeployHistoryController extends BaseController {
    @Autowired
    private ISysDeployHistoryService sysDeployHistoryService;

    /**
     * 查询部署历史管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:deployHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDeployHistory sysDeployHistory) {
        startPage();
        List<SysDeployHistory> list = sysDeployHistoryService.selectSysDeployHistoryList(sysDeployHistory);
        return getDataTable(list);
    }

    /**
     * 导出部署历史管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:deployHistory:export')")
    @Log(title = "部署历史管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysDeployHistory sysDeployHistory) {
        List<SysDeployHistory> list = sysDeployHistoryService.selectSysDeployHistoryList(sysDeployHistory);
        ExcelUtil<SysDeployHistory> util = new ExcelUtil<SysDeployHistory>(SysDeployHistory. class);
        return util.exportExcel(list, "deployHistory");
    }

    /**
     * 获取部署历史管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mnt:deployHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(sysDeployHistoryService.selectSysDeployHistoryById(id));
    }

    /**
     * 新增部署历史管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:deployHistory:add')")
    @Log(title = "部署历史管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDeployHistory sysDeployHistory) {
        return toAjax(sysDeployHistoryService.insertSysDeployHistory(sysDeployHistory));
    }

    /**
     * 修改部署历史管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:deployHistory:edit')")
    @Log(title = "部署历史管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDeployHistory sysDeployHistory) {
        return toAjax(sysDeployHistoryService.updateSysDeployHistory(sysDeployHistory));
    }

    /**
     * 删除部署历史管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:deployHistory:remove')")
    @Log(title = "部署历史管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(sysDeployHistoryService.deleteSysDeployHistoryByIds(ids));
    }
}

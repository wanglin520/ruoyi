package com.ruoyi.project.tool.job.controller;

import com.ruoyi.project.tool.job.domain.SysJobLog;
import com.ruoyi.project.tool.job.service.ISysJobLogService;
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
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

import java.util.List;

/**
 * 定时任务日志Controller
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@RestController
@RequestMapping("/tool/joblog")
public class SysJobLogController extends BaseController {
    @Autowired
    private ISysJobLogService sysJobLogService;

    /**
     * 查询定时任务日志列表
     */
    @PreAuthorize("@ss.hasPermi('tool:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysJobLog sysJobLog) {
        startPage();
        List<SysJobLog> list = sysJobLogService.selectSysJobLogList(sysJobLog);
        return getDataTable(list);
    }

    /**
     * 导出定时任务日志列表
     */
    @PreAuthorize("@ss.hasPermi('tool:log:export')")
    @Log(title = "定时任务日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysJobLog sysJobLog) {
        List<SysJobLog> list = sysJobLogService.selectSysJobLogList(sysJobLog);
        ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog. class);
        return util.exportExcel(list, "log");
    }

    /**
     * 获取定时任务日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('tool:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysJobLogService.selectSysJobLogById(id));
    }

    /**
     * 新增定时任务日志
     */
    @PreAuthorize("@ss.hasPermi('tool:log:add')")
    @Log(title = "定时任务日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysJobLog sysJobLog) {
        return toAjax(sysJobLogService.insertSysJobLog(sysJobLog));
    }

    /**
     * 修改定时任务日志
     */
    @PreAuthorize("@ss.hasPermi('tool:log:edit')")
    @Log(title = "定时任务日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysJobLog sysJobLog) {
        return toAjax(sysJobLogService.updateSysJobLog(sysJobLog));
    }

    /**
     * 删除定时任务日志
     */
    @PreAuthorize("@ss.hasPermi('tool:log:remove')")
    @Log(title = "定时任务日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysJobLogService.deleteSysJobLogByIds(ids));
    }

    /**
     * 清空定时任务日志
     */
    @PreAuthorize("@ss.hasPermi('tool:log:remove')")
    @Log(title = "定时任务日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        sysJobLogService.cleanJobLog();
        return AjaxResult.success();
    }
}

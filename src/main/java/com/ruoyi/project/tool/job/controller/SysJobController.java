package com.ruoyi.project.tool.job.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.tool.job.domain.SysJob;
import com.ruoyi.project.tool.job.service.ISysJobService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务Controller
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@RestController
@RequestMapping("/tool/job")
public class SysJobController extends BaseController {

    @Autowired
    private ISysJobService sysJobService;

    /**
     * 查询定时任务列表
     */
    @PreAuthorize("@ss.hasPermi('tool:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysJob sysJob) {
        startPage();
        List<SysJob> list = sysJobService.selectSysJobList(sysJob);
        return getDataTable(list);
    }

    /**
     * 导出定时任务列表
     */
    @PreAuthorize("@ss.hasPermi('tool:job:export')")
    @Log(title = "定时任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysJob sysJob) {
        List<SysJob> list = sysJobService.selectSysJobList(sysJob);
        ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob. class);
        return util.exportExcel(list, "job");
    }

    /**
     * 获取定时任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('tool:job:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysJobService.selectSysJobById(id));
    }

    /**
     * 新增定时任务
     */
    @PreAuthorize("@ss.hasPermi('tool:job:add')")
    @Log(title = "定时任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysJob sysJob) {
        return toAjax(sysJobService.insertSysJob(sysJob));
    }

    /**
     * 修改定时任务
     */
    @PreAuthorize("@ss.hasPermi('tool:job:edit')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysJob sysJob) {
        return toAjax(sysJobService.updateSysJob(sysJob));
    }

    /**
     * 删除定时任务
     */
    @PreAuthorize("@ss.hasPermi('tool:job:remove')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        List<SysJob> sysJobList = new ArrayList<>();
        for (Long id : ids) {
            SysJob sysJob = sysJobService.selectSysJobById(id);
            sysJobList.add(sysJob);
        }
        sysJobService.deleteSysJobByIds(sysJobList);
        return AjaxResult.success();
    }

    /**
     * 执行定时任务
     */
    @PreAuthorize("@ss.hasPermi('tool:job:run')")
    @Log(title = "定时任务", businessType = BusinessType.DELETE)
    @GetMapping("/run/{id}")
    public AjaxResult run(@PathVariable Long id) {
        SysJob sysJob = sysJobService.selectSysJobById(id);
        sysJobService.runJobById(sysJob);
        return AjaxResult.success();
    }

    /**
     * 校验cron表达式是否有效
     */
    @ResponseBody
    @PostMapping("/checkCronExpressionIsValid")
    public AjaxResult checkCronExpressionIsValid(@RequestBody SysJob job) {
        boolean validResult = sysJobService.checkCronExpressionIsValid(job.getCronExpression());
        return AjaxResult.success("校验结果", validResult);
    }

    /**
     * 修改定时任务状态
     */
    @PreAuthorize("@ss.hasPermi('tool:job:edit')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/updateIsPause")
    public AjaxResult updateIsPause(@RequestBody SysJob sysJob) {
        return toAjax(sysJobService.updateIsPause(sysJob));
    }
}

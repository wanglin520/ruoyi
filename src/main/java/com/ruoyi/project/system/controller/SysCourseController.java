package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysCourse;
import com.ruoyi.project.system.service.ISysCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程Controller
 *
 * @author ruoyi
 * @date 2020-01-17
 */
@RestController
@RequestMapping("/system/course")
public class SysCourseController extends BaseController {
    @Autowired
    private ISysCourseService sysCourseService;

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('system:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCourse sysCourse) {
        startPage();
        List<SysCourse> list = sysCourseService.selectSysCourseList(sysCourse);
        return getDataTable(list);
    }

    /**
     * 导出课程列表
     */
    @PreAuthorize("@ss.hasPermi('system:course:export')")
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCourse sysCourse) {
        List<SysCourse> list = sysCourseService.selectSysCourseList(sysCourse);
        ExcelUtil<SysCourse> util = new ExcelUtil<SysCourse>(SysCourse.class);
        return util.exportExcel(list, "course");
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:course:query')")
    @GetMapping(value = "/{courseId}")
    public AjaxResult getInfo(@PathVariable("courseId") Long courseId) {
        return AjaxResult.success(sysCourseService.selectSysCourseById(courseId));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('system:course:add')")
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCourse sysCourse) {
        return toAjax(sysCourseService.insertSysCourse(sysCourse));
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('system:course:edit')")
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCourse sysCourse) {
        return toAjax(sysCourseService.updateSysCourse(sysCourse));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('system:course:remove')")
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds) {
        return toAjax(sysCourseService.deleteSysCourseByIds(courseIds));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysCourse sysCourse) {
        sysCourse.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(sysCourseService.updateCourseStatus(sysCourse));
    }
}

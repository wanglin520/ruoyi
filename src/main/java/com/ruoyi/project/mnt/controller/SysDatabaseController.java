package com.ruoyi.project.mnt.controller;

import cn.hutool.core.io.FileUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sql.SqlUtil;
import com.ruoyi.project.mnt.domain.SysDatabase;
import com.ruoyi.project.mnt.service.ISysDatabaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

/**
 * 数据库管理Controller
 *
 * @author wangyg
 * @date 2020-02-15
 */
@RestController
@RequestMapping("/mnt/database")
public class SysDatabaseController extends BaseController {

    // 获取系统临时目录
    private String fileSavePath = System.getProperty("java.io.tmpdir");

    @Autowired
    private ISysDatabaseService sysDatabaseService;

    /**
     * 查询数据库管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDatabase sysDatabase) {
        startPage();
        List<SysDatabase> list = sysDatabaseService.selectSysDatabaseList(sysDatabase);
        return getDataTable(list);
    }

    /**
     * 导出数据库管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:export')")
    @Log(title = "数据库管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysDatabase sysDatabase) {
        List<SysDatabase> list = sysDatabaseService.selectSysDatabaseList(sysDatabase);
        ExcelUtil<SysDatabase> util = new ExcelUtil<SysDatabase>(SysDatabase.class);
        return util.exportExcel(list, "database");
    }

    /**
     * 获取数据库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(sysDatabaseService.selectSysDatabaseById(id));
    }

    /**
     * 新增数据库管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:add')")
    @Log(title = "数据库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDatabase sysDatabase) {
        return toAjax(sysDatabaseService.insertSysDatabase(sysDatabase));
    }

    /**
     * 测试数据库连接
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:testConnect')")
    @Log(title = "数据库管理", businessType = BusinessType.INSERT)
    @PostMapping("/testConnect")
    public AjaxResult testConnect(@RequestBody SysDatabase sysDatabase) {
        Boolean testResult = sysDatabaseService.testConnect(sysDatabase);
        return AjaxResult.success(testResult);
    }

    /**
     * 修改数据库管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:edit')")
    @Log(title = "数据库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDatabase sysDatabase) {
        return toAjax(sysDatabaseService.updateSysDatabase(sysDatabase));
    }

    /**
     * 删除数据库管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:database:remove')")
    @Log(title = "数据库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(sysDatabaseService.deleteSysDatabaseByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('mnt:database:add')")
    @Log(title = "执行SQL脚本", businessType = BusinessType.INSERT)
    @PostMapping(value = "/upload")
    public AjaxResult upload(@RequestParam(value = "id") String id, @RequestParam(value = "file") MultipartFile file)throws Exception{
        SysDatabase database = sysDatabaseService.selectSysDatabaseById(id);
        String fileName;
        if(database != null) {
            fileName = file.getOriginalFilename();
            File executeFile = new File(fileSavePath + fileName);
            FileUtil.del(executeFile);
            file.transferTo(executeFile);
            boolean result = SqlUtil.executeFile(database.getJdbcUrl(), database.getUserName(), database.getPwd(), executeFile);
            return AjaxResult.success(result);
        } else {
            return AjaxResult.success(false);
        }
    }
}

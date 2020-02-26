package com.ruoyi.project.mnt.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ruoyi.project.mnt.domain.SysServer;
import com.ruoyi.project.mnt.service.ISysServerService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 服务器管理Controller
 *
 * @author wangyg
 * @date 2020-02-14
 */
@RestController
@RequestMapping("/mnt/server")
public class SysServerController extends BaseController {
    @Autowired
    private ISysServerService sysServerService;

    /**
     * 查询服务器管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysServer sysServer) {
        startPage();
        List<SysServer> list = sysServerService.selectSysServerList(sysServer);
        return getDataTable(list);
    }

    /**
     * 导出服务器管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:export')")
    @Log(title = "服务器管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysServer sysServer) {
        List<SysServer> list = sysServerService.selectSysServerList(sysServer);
        ExcelUtil<SysServer> util = new ExcelUtil<SysServer>(SysServer. class);
        return util.exportExcel(list, "server");
    }

    /**
     * 获取服务器管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysServerService.selectSysServerById(id));
    }

    /**
     * 新增服务器管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:add')")
    @Log(title = "服务器管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysServer sysServer) {
        return toAjax(sysServerService.insertSysServer(sysServer));
    }

    /**
     * 修改服务器管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:edit')")
    @Log(title = "服务器管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysServer sysServer) {
        return toAjax(sysServerService.updateSysServer(sysServer));
    }

    /**
     * 删除服务器管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:remove')")
    @Log(title = "服务器管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysServerService.deleteSysServerByIds(ids));
    }

    /**
     * 测试登录服务器
     * @param sysServer
     * @return
     */
    @PreAuthorize("@ss.hasPermi('mnt:server:add')")
    @Log(title = "服务器管理", businessType = BusinessType.INSERT)
    @PostMapping("/testConnect")
    public AjaxResult testConnect(@RequestBody SysServer sysServer){
        Boolean testResult = sysServerService.testConnect(sysServer);
        return AjaxResult.success(testResult);
    }

    /**
     * 查询服务器下拉选
     * @return
     */
    @GetMapping("/queryServerSelect")
    public AjaxResult queryServerSelect() {
        List<SysServer> list = sysServerService.queryServerSelect();
        return AjaxResult.success(list);
    }
}

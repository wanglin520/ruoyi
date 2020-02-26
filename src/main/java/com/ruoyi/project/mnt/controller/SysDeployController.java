package com.ruoyi.project.mnt.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.project.mnt.domain.SysDeployHistory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.mnt.domain.SysDeploy;
import com.ruoyi.project.mnt.service.ISysDeployService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 部署管理Controller
 *
 * @author wangyg
 * @date 2020-02-17
 */
@RestController
@RequestMapping("/mnt/deploy")
public class SysDeployController extends BaseController {

    // 获取系统临时目录
    private String fileSavePath = System.getProperty("java.io.tmpdir");

    @Autowired
    private ISysDeployService sysDeployService;

    /**
     * 查询部署管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDeploy sysDeploy) {
        startPage();
        List<SysDeploy> list = sysDeployService.selectSysDeployList(sysDeploy);
        return getDataTable(list);
    }

    /**
     * 导出部署管理列表
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:export')")
    @Log(title = "部署管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysDeploy sysDeploy) {
        List<SysDeploy> list = sysDeployService.selectSysDeployList(sysDeploy);
        ExcelUtil<SysDeploy> util = new ExcelUtil<SysDeploy>(SysDeploy. class);
        return util.exportExcel(list, "deploy");
    }

    /**
     * 获取部署管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysDeployService.selectSysDeployById(id));
    }

    /**
     * 新增部署管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:add')")
    @Log(title = "部署管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDeploy sysDeploy) {
        return toAjax(sysDeployService.insertSysDeploy(sysDeploy));
    }

    /**
     * 修改部署管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:edit')")
    @Log(title = "部署管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDeploy sysDeploy) {
        return toAjax(sysDeployService.updateSysDeploy(sysDeploy));
    }

    /**
     * 删除部署管理
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:remove')")
    @Log(title = "部署管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return sysDeployService.deleteSysDeployById(id);
    }

    /**
     * 启动服务
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:start')")
    @Log(title = "启动服务", businessType = BusinessType.OTHER)
    @PostMapping("/startDeployServer")
    public AjaxResult startDeployServer(@RequestBody SysDeploy sysDeploy) {
        return sysDeployService.startDeployServer(sysDeploy);
    }

    /**
     * 停止服务
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:stop')")
    @Log(title = "停止服务", businessType = BusinessType.OTHER)
    @PostMapping("/stopDeployServer")
    public AjaxResult stopDeployServer(@RequestBody SysDeploy sysDeploy) {
        return sysDeployService.stopDeployServer(sysDeploy);
    }

    /**
     * 一键部署
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:add')")
    @Log(title = "一键部署", businessType = BusinessType.INSERT)
    @PostMapping("/uploadDeploy")
    public AjaxResult uploadDeploy(@RequestParam(value = "id") Long id, @RequestParam(value = "file") MultipartFile file) {
        try {
            if(file != null){
                String fileName = file.getOriginalFilename();
                String extensionName = FileUtils.getExtensionName(fileName);
                if(!"jar".equals(extensionName)) {
                    return AjaxResult.error("文件格式错误, 请上传.jar文件.");
                }
                // 将部署文件保存到零时目录
                File deployFile = new File(fileSavePath + fileName);
                FileUtil.del(deployFile);
                file.transferTo(deployFile);
                return sysDeployService.deploy(fileSavePath + fileName, id);
            } else {
                return AjaxResult.error("没有找到相对应的文件!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 系统还原
     */
    @PreAuthorize("@ss.hasPermi('mnt:deploy:edit')")
    @Log(title = "系统还原", businessType = BusinessType.OTHER)
    @PostMapping("/restore")
    public AjaxResult restore(@RequestBody SysDeployHistory sysDeployHistory) {
        return sysDeployService.restore(sysDeployHistory);
    }

}

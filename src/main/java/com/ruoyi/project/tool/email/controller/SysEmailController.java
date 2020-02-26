package com.ruoyi.project.tool.email.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tool.email.domain.SysEmail;
import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import com.ruoyi.project.tool.email.service.ISysEmailConfigService;
import com.ruoyi.project.tool.email.service.ISysEmailService;
import com.ruoyi.project.tool.job.domain.SysJob;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * 邮件管理Controller
 *
 * @author ruoyi
 * @date 2020-02-13
 */
@RestController
@RequestMapping("/tool/email")
public class SysEmailController extends BaseController {

    @Autowired
    private ISysEmailService sysEmailService;

    @Autowired
    private ISysEmailConfigService sysEmailConfigService;

    /**
     * 查询各邮件数量
     */
    @Log(title = "查询各邮件数量", businessType = BusinessType.OTHER)
    @GetMapping("/count")
    public AjaxResult count() {
        Map<String, Object> countList = sysEmailService.count();
        return AjaxResult.success(countList);
    }

    /**
     * 查询邮件列表
     */
    @PreAuthorize("@ss.hasPermi('tool:email:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysEmail sysEmail) {
        startPage();
        List<SysEmail> list = sysEmailService.selectSysEmailList(sysEmail);
        return getDataTable(list);
    }

    /**
     * 删除邮件(移动到回收站, 将emailType 修改为 4)
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tool:email:remove')")
    @Log(title = "删除邮件", businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        return toAjax(sysEmailService.deleteSysEmailById(id));
    }

    /**
     * 彻底删除邮件
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tool:email:remove')")
    @Log(title = "彻底删除邮件", businessType = BusinessType.DELETE)
    @GetMapping("/compRemove/{id}")
    public AjaxResult compRemove(@PathVariable("id") Long id) {
        return toAjax(sysEmailService.compDeleteSysEmailById(id));
    }

    /**
     * 恢复邮件
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tool:email:edit')")
    @Log(title = "恢复邮件", businessType = BusinessType.UPDATE)
    @GetMapping("/recover/{id}")
    public AjaxResult recover(@PathVariable("id") Long id) {
        return toAjax(sysEmailService.recoverSysEmailById(id));
    }

    /**
     * 发送邮件
     * @param  //sysEmail
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tool:email:send')")
    @Log(title = "发送邮件", businessType = BusinessType.OTHER)
    @PostMapping("/send")
    public AjaxResult send(@RequestBody SysEmail sysEmail) {
        try {
            // 查询当前登录用户邮箱配置信息
            String email = SecurityUtils.getLoginUserEmail();
            SysEmailConfig sysEmailConfig = sysEmailConfigService.selectSysEmailConfigByEmail(email);
            // 发送邮件
            sysEmailService.send(sysEmail, sysEmailConfig);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 存为草稿
     * @param sysEmail 草稿邮件
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tool:email:draft')")
    @Log(title = "存为草稿", businessType = BusinessType.INSERT)
    @PostMapping("/saveDraftEmail")
    public AjaxResult saveDraftEmail(@RequestBody SysEmail sysEmail) {
        try {
            // 查询当前登录用户邮箱配置信息
            String email = SecurityUtils.getLoginUserEmail();
            SysEmailConfig sysEmailConfig = sysEmailConfigService.selectSysEmailConfigByEmail(email);

            // 保存草稿
            int num = sysEmailService.saveDraftEmail(sysEmail, sysEmailConfig);
            return toAjax(num);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


}

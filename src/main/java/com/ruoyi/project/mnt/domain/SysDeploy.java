package com.ruoyi.project.mnt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 部署管理对象 sys_deploy
 *
 * @author wangyg
 * @date 2020-02-17
 */
public class SysDeploy extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 应用编号
     */
    @Excel(name = "应用编号")
    private Long appId;

    /**
     * 应用对象
     */
    @Excel(name = "应用名称", targetAttr = "name", type = Excel.Type.EXPORT)
    private SysApp sysApp;

    /**
     * 服务器编号
     */
    @Excel(name = "服务器编号")
    private Long serverId;

    /**
     * 服务器对象
     */
    @Excel(name = "服务器名称", targetAttr = "name", type = Excel.Type.EXPORT)
    private SysServer sysServer;

    /**
     * 状态：1停止、0启动
     */
    @Excel(name = "状态：1停止、0启动")
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getAppId() {
        return appId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SysApp getSysApp() {
        return sysApp;
    }

    public void setSysApp(SysApp sysApp) {
        this.sysApp = sysApp;
    }

    public SysServer getSysServer() {
        return sysServer;
    }

    public void setSysServer(SysServer sysServer) {
        this.sysServer = sysServer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("appId", getAppId())
                .append("serverId", getServerId())
                .append("sysApp", getSysApp())
                .append("sysServer", getSysServer())
                .append("createTime", getCreateTime())
                .toString();
    }
}

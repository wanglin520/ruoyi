package com.ruoyi.project.mnt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 应用管理对象 sys_app
 *
 * @author wangyg
 * @date 2020-02-17
 */
public class SysApp extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    private Long id;

    /**
     * 应用名称
     */
    @Excel(name = "应用名称")
    private String name;

    /**
     * 上传目录
     */
    @Excel(name = "上传目录")
    private String uploadPath;

    /**
     * 部署路径
     */
    @Excel(name = "部署路径")
    private String deployPath;

    /**
     * 备份路径
     */
    @Excel(name = "备份路径")
    private String backupPath;

    /**
     * 应用端口
     */
    @Excel(name = "应用端口")
    private Long port;

    /**
     * 启动脚本
     */
    @Excel(name = "启动脚本")
    private String startScript;

    /**
     * 部署脚本
     */
    @Excel(name = "部署脚本")
    private String deployScript;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setDeployPath(String deployPath) {
        this.deployPath = deployPath;
    }

    public String getDeployPath() {
        return deployPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public Long getPort() {
        return port;
    }

    public void setStartScript(String startScript) {
        this.startScript = startScript;
    }

    public String getStartScript() {
        return startScript;
    }

    public void setDeployScript(String deployScript) {
        this.deployScript = deployScript;
    }

    public String getDeployScript() {
        return deployScript;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("uploadPath", getUploadPath())
                .append("deployPath", getDeployPath())
                .append("backupPath", getBackupPath())
                .append("port", getPort())
                .append("startScript", getStartScript())
                .append("deployScript", getDeployScript())
                .append("createTime", getCreateTime())
                .toString();
    }
}

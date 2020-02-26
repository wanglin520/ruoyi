package com.ruoyi.project.mnt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 部署历史管理对象 sys_deploy_history
 *
 * @author wangyg
 * @date 2020-02-17
 */
public class SysDeployHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 应用名称
     */
    @Excel(name = "应用名称")
    private String appName;

    /**
     * 部署日期
     */
    @Excel(name = "部署日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deployDate;

    /**
     * 部署用户
     */
    @Excel(name = "部署用户")
    private String deployUser;

    /**
     * 服务器IP
     */
    @Excel(name = "服务器IP")
    private String ip;

    /**
     * 部署编号
     */
    @Excel(name = "部署编号")
    private Long deployId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setDeployDate(Date deployDate) {
        this.deployDate = deployDate;
    }

    public Date getDeployDate() {
        return deployDate;
    }

    public void setDeployUser(String deployUser) {
        this.deployUser = deployUser;
    }

    public String getDeployUser() {
        return deployUser;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setDeployId(Long deployId) {
        this.deployId = deployId;
    }

    public Long getDeployId() {
        return deployId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("appName", getAppName())
                .append("deployDate", getDeployDate())
                .append("deployUser", getDeployUser())
                .append("ip", getIp())
                .append("deployId", getDeployId())
                .toString();
    }
}

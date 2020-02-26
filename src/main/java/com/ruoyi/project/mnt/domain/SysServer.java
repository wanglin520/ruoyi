package com.ruoyi.project.mnt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 服务器管理对象 sys_server
 *
 * @author wangyg
 * @date 2020-02-14
 */
public class SysServer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 账号
     */
    @Excel(name = "账号" )
    private String account;

    /**
     * IP地址
     */
    @Excel(name = "IP地址" )
    private String ip;

    /**
     * 服务器名称
     */
    @Excel(name = "服务器名称" )
    private String name;

    /**
     * 密码
     */
    @Excel(name = "密码" )
    private String password;

    /**
     * 端口
     */
    @Excel(name = "端口" )
    private Integer port;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , getId())
                .append("account" , getAccount())
                .append("ip" , getIp())
                .append("name" , getName())
                .append("password" , getPassword())
                .append("port" , getPort())
                .append("createTime" , getCreateTime())
                .toString();
    }
}

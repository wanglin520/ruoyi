package com.ruoyi.project.mnt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 数据库管理对象 sys_database
 *
 * @author wangyg
 * @date 2020-02-15
 */
public class SysDatabase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * jdbc连接
     */
    @Excel(name = "jdbc连接")
    private String jdbcUrl;

    /**
     * 账号
     */
    @Excel(name = "账号")
    private String userName;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String pwd;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("jdbcUrl", getJdbcUrl())
                .append("userName", getUserName())
                .append("pwd", getPwd())
                .append("createTime", getCreateTime())
                .toString();
    }
}

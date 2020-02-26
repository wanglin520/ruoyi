package com.ruoyi.project.tool.job.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 定时任务对象 sys_job
 *
 * @author ruoyi
 * @date 2020-02-13
 */
public class SysJob extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String JOB_KEY = "JOB_KEY";

    /**
     * ID
     */
    private Long id;

    /**
     * Spring Bean名称
     */
    @Excel(name = "Spring Bean名称")
    private String beanName;

    /**
     * cron 表达式
     */
    @Excel(name = "cron 表达式")
    private String cronExpression;

    /**
     * 状态：1暂停、0启用
     */
    @Excel(name = "状态：1暂停、0启用")
    private String isPause;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String jobName;

    /**
     * 方法名称
     */
    @Excel(name = "方法名称")
    private String methodName;

    /**
     * 参数
     */
    @Excel(name = "参数")
    private String jobParams;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setIsPause(String isPause) {
        this.isPause = isPause;
    }

    public String getIsPause() {
        return isPause;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setJobParams(String jobParams) {
        this.jobParams = jobParams;
    }

    public String getJobParams() {
        return jobParams;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("beanName", getBeanName())
                .append("cronExpression", getCronExpression())
                .append("isPause", getIsPause())
                .append("jobName", getJobName())
                .append("methodName", getMethodName())
                .append("jobParams", getJobParams())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .toString();
    }
}

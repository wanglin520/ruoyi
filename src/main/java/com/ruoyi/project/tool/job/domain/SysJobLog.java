package com.ruoyi.project.tool.job.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 定时任务日志对象 sys_job_log
 *
 * @author ruoyi
 * @date 2020-02-13
 */
public class SysJobLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    private Long id;

    /**
     * Bean名称
     */
    @Excel(name = "Bean名称")
    private String beanName;

    /**
     * CRON表达式
     */
    @Excel(name = "CRON表达式")
    private String cronExpression;

    /**
     * 异常信息
     */
    @Excel(name = "异常信息")
    private String exceptionDetail;

    /**
     * 执行结果(0：成功 1：失败)
     */
    @Excel(name = "执行结果(0：成功 1：失败)")
    private String isSuccess;

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

    /**
     * 时间
     */
    @Excel(name = "时间")
    private Long time;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBeanName(String beanName) {
        this.beanName= beanName;
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

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getIsSuccess() {
        return isSuccess;
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

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("beanName", getBeanName())
                .append("createTime", getCreateTime())
                .append("cronExpression", getCronExpression())
                .append("exceptionDetail", getExceptionDetail())
                .append("isSuccess", getIsSuccess())
                .append("jobName", getJobName())
                .append("methodName", getMethodName())
                .append("jobParams", getJobParams())
                .append("time", getTime())
                .toString();
    }
}

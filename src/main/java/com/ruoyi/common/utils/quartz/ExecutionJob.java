package com.ruoyi.common.utils.quartz;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ThrowableUtil;
import com.ruoyi.common.utils.spring.SpringContextHolder;
import com.ruoyi.framework.config.thread.ThreadPoolExecutorUtil;
import com.ruoyi.project.tool.job.domain.SysJob;
import com.ruoyi.project.tool.job.domain.SysJobLog;
import com.ruoyi.project.tool.job.mapper.SysJobLogMapper;
import com.ruoyi.project.tool.job.service.ISysJobService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 * @author /
 * @date 2019-01-07
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 该处仅供参考 */
    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getPoll();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        SysJob quartzJob = (SysJob) context.getMergedJobDataMap().get(SysJob.JOB_KEY);
        // 获取spring bean
        SysJobLogMapper sysJobLogMapper = SpringContextHolder.getBean(SysJobLogMapper.class);
        ISysJobService quartzJobService = SpringContextHolder.getBean(ISysJobService.class);

        SysJobLog log = new SysJobLog();
        log.setJobName(quartzJob.getJobName());
        log.setBeanName(quartzJob.getBeanName());
        log.setMethodName(quartzJob.getMethodName());
        log.setParams(quartzJob.getParams());
        log.setCreateTime(DateUtils.getNowDate());
        long startTime = System.currentTimeMillis();
        log.setCronExpression(quartzJob.getCronExpression());
        try {
            // 执行任务
            logger.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                    quartzJob.getJobParams());
            Future<?> future = EXECUTOR.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态
            log.setIsSuccess("0");
            logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;
            log.setTime(times);
            // 任务状态 0：成功 1：失败
            log.setIsSuccess("1");
            log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
            quartzJob.setIsPause("1");
            // 更新状态
            quartzJobService.updateIsPause(quartzJob);
        } finally {
            sysJobLogMapper.insertSysJobLog(log);
        }
    }
}

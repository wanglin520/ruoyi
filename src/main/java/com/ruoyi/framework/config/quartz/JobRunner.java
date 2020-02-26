package com.ruoyi.framework.config.quartz;

import com.ruoyi.common.utils.quartz.QuartzManage;
import com.ruoyi.project.tool.job.domain.SysJob;
import com.ruoyi.project.tool.job.mapper.SysJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Component
public class JobRunner implements ApplicationRunner {

    @Autowired
    private SysJobMapper sysJobMapper;

    @Autowired
    private QuartzManage quartzManage;

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        System.out.println("--------------------注入定时任务---------------------");
        List<SysJob> quartzJobs = sysJobMapper.findByIsPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
        System.out.println("--------------------定时任务注入完成---------------------");
    }
}

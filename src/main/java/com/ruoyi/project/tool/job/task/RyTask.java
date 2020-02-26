package com.ruoyi.project.tool.job.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 定时任务调度测试
 * ("ryTask")
 * @author ruoyi
 */
//@Component
public class RyTask implements Job {

    @Autowired
    private ISysUserService iSysUserService;

    /*public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    public void queryUserList() {
        SysUser sysUser = new SysUser();
        List<SysUser> userList = iSysUserService.selectUserList(sysUser);
        if(!StringUtils.isEmpty(userList)) {
            for (SysUser user : userList) {
                System.out.println(user.toString());
            }
        }
    }*/

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SysUser sysUser = new SysUser();
        List<SysUser> userList = iSysUserService.selectUserList(sysUser);
        if(!StringUtils.isEmpty(userList)) {
            for (SysUser user : userList) {
                System.out.println(user.toString());
            }
        }
    }
}

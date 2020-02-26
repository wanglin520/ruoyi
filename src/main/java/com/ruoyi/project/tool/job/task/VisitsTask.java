package com.ruoyi.project.tool.job.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-25
 */
@Component
public class VisitsTask {

    @Autowired
    private ISysUserService iSysUserService;

//    public VisitsTask(VisitsService visitsService) {
//        this.visitsService = visitsService;
//    }

    public void run(){
        iSysUserService.print();
    }
}

package com.ruoyi.project.mnt.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.Threads;
import com.ruoyi.common.utils.shell.ExecuteShellUtil;
import com.ruoyi.common.utils.shell.ScpClientUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.mnt.domain.SysApp;
import com.ruoyi.project.mnt.domain.SysDeployHistory;
import com.ruoyi.project.mnt.domain.SysServer;
import com.ruoyi.project.mnt.mapper.SysAppMapper;
import com.ruoyi.project.mnt.mapper.SysDeployHistoryMapper;
import com.ruoyi.project.mnt.mapper.SysServerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mnt.mapper.SysDeployMapper;
import com.ruoyi.project.mnt.domain.SysDeploy;
import com.ruoyi.project.mnt.service.ISysDeployService;

/**
 * 部署管理Service业务层处理
 *
 * @author wangyg
 * @date 2020-02-17
 */
@Slf4j
@Service
public class SysDeployServiceImpl implements ISysDeployService {

    private final String FILE_SEPARATOR = "/";

    @Autowired
    private SysDeployMapper sysDeployMapper;

    @Autowired
    private SysServerMapper sysServerMapper;

    @Autowired
    private SysAppMapper sysAppMapper;

    @Autowired
    private SysDeployHistoryMapper sysDeployHistoryMapper;

    /**
     * 查询部署管理
     *
     * @param id 部署管理ID
     * @return 部署管理
     */
    @Override
    public SysDeploy selectSysDeployById(Long id) {
        return sysDeployMapper.selectSysDeployById(id);
    }

    /**
     * 查询部署管理列表
     *
     * @param sysDeploy 部署管理
     * @return 部署管理
     */
    @Override
    public List<SysDeploy> selectSysDeployList(SysDeploy sysDeploy) {
        return sysDeployMapper.selectSysDeployList(sysDeploy);
    }

    /**
     * 新增部署管理
     *
     * @param sysDeploy 部署管理
     * @return 结果
     */
    @Override
    public int insertSysDeploy(SysDeploy sysDeploy) {
        sysDeploy.setCreateTime(DateUtils.getNowDate());
        return sysDeployMapper.insertSysDeploy(sysDeploy);
    }

    /**
     * 修改部署管理
     *
     * @param sysDeploy 部署管理
     * @return 结果
     */
    @Override
    public int updateSysDeploy(SysDeploy sysDeploy) {
        return sysDeployMapper.updateSysDeploy(sysDeploy);
    }

    /**
     * 批量删除部署管理
     *
     * @param ids 需要删除的部署管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDeployByIds(Long[] ids) {
        return sysDeployMapper.deleteSysDeployByIds(ids);
    }

    /**
     * 删除部署管理信息
     *
     * @param id 部署管理ID
     * @return 结果
     */
    @Override
    public AjaxResult deleteSysDeployById(Long id) {
        SysDeploy sysDeploy = sysDeployMapper.selectSysDeployById(id);
        // 通过id 查询服务器信息
        SysServer sysServer = sysServerMapper.selectSysServerById(sysDeploy.getServerId());
        String status = sysDeploy.getStatus();
        // 正在运行的部署需要先停止服务
        if("0".equals(status)) {
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(sysServer);
            // 判断Session是否可以连接
            if(!executeShellUtil.isConnecte()) {
                return AjaxResult.error("服务器连接失败, 删除应用失败!");
            }
            SysApp sysApp = sysAppMapper.selectSysAppById(sysDeploy.getAppId());
            // 发送停止应用命令
            log.info("发送停止应用命令");
            stopApp(sysApp.getPort(), executeShellUtil);
            // 等待1s
            Threads.sleep(1 * 1000);
            // 查询状态
            boolean result = checkIsRunningStatus(sysApp.getPort(), executeShellUtil);
            executeShellUtil.close();
            log.info("======状态值: {}", result);
            if(!result) {
                log.error("停止失败");
                return AjaxResult.error("应用停止失败, 删除应用失败");
            }
        }
        int num = sysDeployMapper.deleteSysDeployById(id);
        if(num > 0) {
            return AjaxResult.success("删除应用成功");
        } else {
            return AjaxResult.error("删除应用失败");
        }
    }

    /**
     * 启动服务
     *
     * @param sysDeploy
     */
    @Override
    public AjaxResult startDeployServer(SysDeploy sysDeploy) {
        StringBuilder sb = new StringBuilder();
        try {
            // 通过server_id 查询服务器详细信息
            SysServer sysServer = sysServerMapper.selectSysServerById(sysDeploy.getServerId());
            if(sysServer == null) {
                log.error("服务器信息不存在");
                return AjaxResult.error("服务器信息不存在");
            } else {
                ExecuteShellUtil executeShellUtil = getExecuteShellUtil(sysServer);
                // 判断Session是否可以连接
                if(!executeShellUtil.isConnecte()) {
                    return AjaxResult.error("服务器连接失败, 删除应用失败!");
                }
                // new ExecuteShellUtil(sysServer.getIp(), sysServer.getAccount(), sysServer.getPassword(), sysServer.getPort());
                // 为了防止重复启动，这里先停止应用
                SysApp sysApp = sysAppMapper.selectSysAppById(sysDeploy.getAppId());
                // 发送停止应用命令
                log.info("发送停止应用命令");
                stopApp(sysApp.getPort(), executeShellUtil);
                // 发送启动命令
                executeShellUtil.execute(sysApp.getStartScript());
                // 等待3秒钟
                Threads.sleep(3 * 1000);
                log.info("应用启动中，请耐心等待启动结果，或者稍后手动查看运行状态");
                boolean result = false;
                // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
                for (int i=0; i< 1; i++){
                    result = checkIsRunningStatus(sysApp.getPort(), executeShellUtil);
                    if(result){
                        break;
                    }
                    // 休眠6秒
                    Threads.sleep(1 * 1000);
                }
                sb.append("服务器:").append(sysServer.getName()).append("<br>");
                sb.append("应用:").append(sysApp.getName()).append("<br>");
                if(result) {
                    log.info("修改部署应用状态值");
                    sysDeploy.setStatus("0");
                    sysDeployMapper.updateSysDeploy(sysDeploy);
                    sb.append("启动成功!");
                    return AjaxResult.success(sb.toString());
                } else {
                    sb.append("启动失败!");
                    return AjaxResult.error(sb.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 停止服务
     *
     * @param sysDeploy
     * @return
     */
    @Override
    public AjaxResult stopDeployServer(SysDeploy sysDeploy) {
        StringBuilder sb = new StringBuilder();
        try {
            // 通过server_id 查询服务器详细信息
            SysServer sysServer = sysServerMapper.selectSysServerById(sysDeploy.getServerId());
            if(sysServer == null) {
                log.error("服务器信息不存在");
                return AjaxResult.error("服务器信息不存在");
            } else {
                ExecuteShellUtil executeShellUtil = getExecuteShellUtil(sysServer);
                // 判断Session是否可以连接
                if(!executeShellUtil.isConnecte()) {
                    return AjaxResult.error("服务器连接失败, 删除应用失败!");
                }
                // new ExecuteShellUtil(sysServer.getIp(), sysServer.getAccount(), sysServer.getPassword(), sysServer.getPort());
                SysApp sysApp = sysAppMapper.selectSysAppById(sysDeploy.getAppId());
                // 发送停止应用命令
                log.info("发送停止应用命令");
                stopApp(sysApp.getPort(), executeShellUtil);
                // 等待1s
                Threads.sleep(1 * 1000);

                sb.append("服务器:").append(sysServer.getName()).append("<br>");
                sb.append("应用:").append(sysApp.getName()).append("<br>");
                boolean result = checkIsRunningStatus(sysApp.getPort(), executeShellUtil);
                executeShellUtil.close();
                if (result) {
                    sb.append("停止失败!");
                    log.error(sb.toString());
                    return AjaxResult.error(sb.toString());
                } else {
                    log.info("修改部署应用状态值");
                    sysDeploy.setStatus("1");
                    sysDeployMapper.updateSysDeploy(sysDeploy);

                    sb.append("停止成功!");
                    log.info(sb.toString());
                    return AjaxResult.success(sb.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 一键部署
     * @param fileSavePath 文件路径
     * @param id 应用ID
     */
    @Override
    public AjaxResult deploy(String fileSavePath, Long id) {
        StringBuilder sb = new StringBuilder();
        try {
            SysDeploy sysDeploy = sysDeployMapper.selectSysDeployById(id);
            if (sysDeploy == null) {
                log.error("部署信息不存在");
                return AjaxResult.error("部署信息不存在");
            }
            SysApp sysApp = sysAppMapper.selectSysAppById(sysDeploy.getAppId());
            if (sysApp == null) {
                log.error("包对应应用信息不存在");
                return AjaxResult.error("包对应应用信息不存在");
            }
            SysServer sysServer = sysServerMapper.selectSysServerById(sysDeploy.getServerId());
            if (sysServer == null) {
                log.error("包对应服务器信息不存在");
                return AjaxResult.error("包对应服务器信息不存在");
            }
            // 初始化Session
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(sysServer);
            // 确认服务器上有这些目录
            log.info("判断上传目录, 部署目录, 备份目录是否存在");
            executeShellUtil.execute("mkdir -p " + sysApp.getUploadPath());
            executeShellUtil.execute("mkdir -p " + sysApp.getBackupPath());
            executeShellUtil.execute("mkdir -p " + sysApp.getDeployPath());
            // 登陆到服务器
            log.info(String.format("登陆到服务器:%s", sysServer.getIp()));
            ScpClientUtil scpClientUtil = getScpClientUtil(sysServer);

            // 将部署文件从零时目录上传到上传目录
            log.info(String.format("上传文件到服务器:%s , 目录:%s下，请稍等...", sysServer.getIp(), sysApp.getUploadPath()));
            scpClientUtil.putFile(fileSavePath, sysApp.getUploadPath());

            // 判断是否第一次部署, 如果不是则需要将之前的部署进行备份
            boolean flag = checkFile(executeShellUtil, sysApp);
            if (flag) {
                // 停止应用
                log.info("---停止原来应用---");
                stopApp(sysApp.getPort(), executeShellUtil);
                // 备份应用
                log.info("---备份原来应用---");
                backupApp(executeShellUtil, sysServer.getIp(), sysApp.getDeployPath(), sysApp.getName(), sysApp.getBackupPath(), id);
            }
            log.info("部署应用");
            // 部署文件,并启动应用
            String deployScript = sysApp.getDeployScript();
            executeShellUtil.execute(deployScript);
            Threads.sleep(3 * 1000);
            log.info("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态");
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            for(int i=0; i<30; i++) {
                result = checkIsRunningStatus(sysApp.getPort(), executeShellUtil);
                if(result){
                    break;
                }
                // 休眠6秒
                Threads.sleep(6 * 1000);
            }
            sb.append("服务器: ").append(sysServer.getName()).append("<br>");
            sb.append("应用: ").append(sysApp.getName()).append("<br>");
            // 关闭Session
            executeShellUtil.close();
            if(result) {
                sb.append("启动成功!");
                log.info("启动信息: {}", sb.toString());
                return AjaxResult.success(sb.toString());
            } else {
                sb.append("启动失败!");
                log.error("启动信息: {}", sb.toString());
                return AjaxResult.error(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 系统还原
     *
     * @param sysDeployHistory 需要还原的备份
     * @return
     */
    @Override
    public AjaxResult restore(SysDeployHistory sysDeployHistory) {
        // 通过id 查询部署详细信息
        SysDeploy sysDeploy = sysDeployMapper.selectSysDeployById(sysDeployHistory.getDeployId());
        // 通过id 查询应用信息
        SysApp sysApp = sysAppMapper.selectSysAppById(sysDeploy.getAppId());
        if(sysApp == null) {
            return AjaxResult.error("应用信息不存在");
        }
        // 判断备份路径是否以 / 结尾, 如果不是则在备份路径结尾加上/
        String backupPath = sysApp.getBackupPath();
        if (!backupPath.endsWith(FILE_SEPARATOR)) {
            backupPath += FILE_SEPARATOR;
        }

        // 初始化Session
        SysServer sysServer = sysServerMapper.selectSysServerById(sysDeploy.getServerId());
        ExecuteShellUtil executeShellUtil = getExecuteShellUtil(sysServer);
        log.info(String.format("登陆到服务器:%s", sysServer.getIp()));
        log.info("---停止原来应用---");
        // <1>停止应用
        stopApp(sysApp.getPort(), executeShellUtil);
        // <2>删除原来应用
        log.info("---删除原来应用---");
        executeShellUtil.execute("rm -rf " + sysApp.getDeployPath() + FILE_SEPARATOR + sysDeployHistory.getAppName());
        // <3>还原应用
        log.info("---还原应用---");
        executeShellUtil.execute("cp -r " + backupPath + "/. " + sysApp.getDeployPath());
        // <4>启动应用
        log.info("---启动应用---");
        executeShellUtil.execute(sysApp.getStartScript());
        log.info("应用启动中，请耐心等待启动结果，或者稍后手动查看启动状态");
        boolean result = false;
        // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
        for(int i=0; i<30; i++){
            result = checkIsRunningStatus(sysApp.getPort(), executeShellUtil);
            if(result){
                break;
            }
            // 休眠6秒
            Threads.sleep(6 * 1000);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("服务器:").append(sysServer.getIp()).append("<br>");
        sb.append("应用:").append(sysDeployHistory.getAppName()).append("<br>");
        executeShellUtil.close();
        if (result) {
            sb.append("启动成功!");
            log.info(sb.toString());
            return AjaxResult.success(sb.toString());
        } else {
            sb.append("启动失败!");
            log.info(sb.toString());
            return AjaxResult.error(sb.toString());
        }
    }

    /**
     * 内部方法: 初始化Session
     * @param SysServer
     * @return
     */
    private ExecuteShellUtil getExecuteShellUtil(SysServer SysServer) {
        return new ExecuteShellUtil(SysServer.getIp(), SysServer.getAccount(), SysServer.getPassword(), SysServer.getPort());
    }

    /**
     * 内部方法: 指定端口程序是否在运行
     * @param port 端口
     * @param executeShellUtil /
     * @return true 正在运行  false 已经停止
     */
    private boolean checkIsRunningStatus(long port, ExecuteShellUtil executeShellUtil) {
        String result = executeShellUtil.executeForResult(String.format("fuser -n tcp %d", port));
        if(StringUtils.isNotEmpty(result)) {
            return result.indexOf("/tcp:")>0;
        } else {
            return false;
        }
    }

    /**
     * 内部方法: 停止App应用
     * @param port 端口
     * @param executeShellUtil /
     */
    private void stopApp(long port, ExecuteShellUtil executeShellUtil) {
        //发送停止命令
        executeShellUtil.execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));
    }

    /**
     * 内部方法: 判断是否第一次部署
     * @param executeShellUtil
     * @param sysApp
     * @return
     */
    private boolean checkFile(ExecuteShellUtil executeShellUtil, SysApp sysApp) {
        String result = executeShellUtil.executeForResult("find " + sysApp.getDeployPath() + " -name " + sysApp.getName());
        if(StringUtils.isNotEmpty(result)) {
            return result.indexOf(sysApp.getName())>0;
        } else {
            return false;
        }
    }

    /**
     * 内部方法: 初始化远程命令行
     * @param sysServer
     * @return
     */
    private ScpClientUtil getScpClientUtil(SysServer sysServer) {
        return ScpClientUtil.getInstance(sysServer.getIp(), sysServer.getPort(), sysServer.getAccount(), sysServer.getPassword());
    }

    /**
     * 内部方法: 备份应用 -- 备份路径: backupPath/应用名称/部署时间(yyyyMMddHHmmss)
     * @param executeShellUtil
     * @param ip 服务器IP
     * @param deployPath 部署路径
     * @param appName 应用名称
     * @param backupPath 备份路径
     * @param id 部署ID
     */
    private void backupApp(ExecuteShellUtil executeShellUtil, String ip, String deployPath, String appName, String backupPath, Long id) {
        // 格式化部署时间
        String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        StringBuilder sb = new StringBuilder();
        String endsWith = "\\";
        // 判断备份路径是否以 / 结尾, 如果没有则在备份路径后加上 /
        if (!backupPath.endsWith(FILE_SEPARATOR) && !backupPath.endsWith(endsWith)) {
            backupPath += FILE_SEPARATOR;
        }
        backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
        log.info("创建备份文件夹: {}", backupPath);
        sb.append("mkdir -p ").append(backupPath);
        log.info("将原部署文件移动到新建的备份文件夹");
        sb.append("mv -f ").append(deployPath);
        // 判断备份路径是否以 / 结尾
        if (!deployPath.endsWith(FILE_SEPARATOR)) {
            sb.append(FILE_SEPARATOR);
        }
        sb.append(appName).append(" ").append(backupPath);
        // 执行备份应用脚本
        log.info("执行部署文件脚本: {}", sb.toString());
        executeShellUtil.execute(sb.toString());
        // 插入部署备份信息
        SysDeployHistory sysDeployHistory = new SysDeployHistory();
        sysDeployHistory.setAppName(appName);
        sysDeployHistory.setDeployUser(SecurityUtils.getUsername());
        sysDeployHistory.setDeployDate(DateUtils.getNowDate());
        sysDeployHistory.setIp(ip);
        sysDeployHistory.setDeployId(id);
        sysDeployHistoryMapper.insertSysDeployHistory(sysDeployHistory);
    }


}

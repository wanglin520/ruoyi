package com.ruoyi.project.tool.email.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.ruoyi.common.exception.BadRequestException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.EncryptUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.tool.email.domain.SysEmail;
import com.ruoyi.project.tool.email.domain.SysEmailConfig;
import com.ruoyi.project.tool.email.mapper.SysEmailMapper;
import com.ruoyi.project.tool.email.service.ISysEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysEmailServiceImpl implements ISysEmailService {

    @Autowired
    private SysEmailMapper sysEmailMapper;

    /**
     * 查询各邮件数量
     *
     * @return
     */
    @Override
    public Map<String, Object> count() {
        Map resultMap = new HashMap();
        String email = SecurityUtils.getLoginUserEmail();
        // 发件箱数量
        Map fParamMap = new HashMap();
        fParamMap.put("fromEmail", email);
        fParamMap.put("emailType", 0);
        Integer fcount = sysEmailMapper.queryCount(fParamMap);

        if(fcount != null) {
            resultMap.put("fcount", fcount);
        } else {
            resultMap.put("fcount", 0);
        }
        // 收件箱数量
        Map sParamMap = new HashMap();
        sParamMap.put("toEmail", email);
        sParamMap.put("emailType", 0);
        Integer scount = sysEmailMapper.queryCount(sParamMap);
        if(scount != null) {
            resultMap.put("scount", scount);
        } else {
            resultMap.put("scount", 0);
        }

        // 草稿箱数量
        Map cParamMap = new HashMap();
        cParamMap.put("fromEmail", email);
        cParamMap.put("emailType", 3);
        Integer ccount = sysEmailMapper.queryCount(cParamMap);
        if(ccount != null) {
            resultMap.put("ccount", ccount);
        } else {
            resultMap.put("ccount", 0);
        }

        // 垃圾箱数量(收件或者发件只要是删除了均可以为垃圾邮件)
        Map lParamMap = new HashMap();
        lParamMap.put("email", email);
        lParamMap.put("emailType", 4);
        Integer lcount = sysEmailMapper.queryCount(lParamMap);
        if(lcount != null) {
            resultMap.put("lcount", lcount);
        } else {
            resultMap.put("lcount", 0);
        }
        return resultMap;
    }

    /**
     * 查询邮件列表
     *
     * @param sysEmail 邮件
     * @return 邮件
     */
    @Override
    public List<SysEmail> selectSysEmailList(SysEmail sysEmail) {
        String email = SecurityUtils.getLoginUserEmail();
        Integer emailType = sysEmail.getEmailType();
        Map paramMap = new HashMap();
        if(emailType == 1) {
            // 收件箱
            paramMap.put("toEmail", email);
            paramMap.put("emailType", 0);
        } else if (emailType == 2) {
            // 发件箱
            paramMap.put("fromEmail", email);
            paramMap.put("emailType", 0);
        } else if (emailType == 3) {
            // 草稿箱
            paramMap.put("fromEmail", email);
            paramMap.put("emailType", 3);
        } else if (emailType == 4) {
            // 垃圾箱(收件或者发件只要是删除了均可以为垃圾邮件)
            paramMap.put("email", email);
            paramMap.put("emailType", 4);
        }
        return sysEmailMapper.selectSysEmailList(paramMap);
    }

    /**
     * 新增邮件
     *
     * @param sysEmail 邮件
     * @return 结果
     */
    @Override
    public int insertSysEmail(SysEmail sysEmail) {
        return sysEmailMapper.insertSysEmail(sysEmail);
    }

    /**
     * 删除邮件信息
     *
     * @param id 邮件ID
     * @return 结果
     */
    @Override
    public int deleteSysEmailById(Long id) {
        SysEmail sysEmail = sysEmailMapper.selectSysEmailById(id);
        sysEmail.setEmailType(4);
        return sysEmailMapper.updateSysEmail(sysEmail);
    }

    /**
     * 彻底删除邮件
     *
     * @param id
     * @return
     */
    @Override
    public int compDeleteSysEmailById(Long id) {
        return sysEmailMapper.deleteSysEmailById(id);
    }

    /**
     * 恢复邮件
     *
     * @param id
     * @return
     */
    @Override
    public int recoverSysEmailById(Long id) {
        SysEmail sysEmail = sysEmailMapper.selectSysEmailById(id);
        sysEmail.setEmailType(0);
        return sysEmailMapper.updateSysEmail(sysEmail);
    }

    /**
     * 发送邮件
     *
     * @param sysEmail     邮件发送的内容
     * @param sysEmailConfig 邮件配置
     */
    @Override
    public void send(SysEmail sysEmail, SysEmailConfig sysEmailConfig) {
        if (sysEmailConfig == null) {
            throw new BadRequestException("请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setHost(sysEmailConfig.getHost());
        account.setPort(Integer.parseInt(sysEmailConfig.getPort()));
        account.setAuth(true);
        account.setFrom(sysEmailConfig.getUsername() + "<" + sysEmailConfig.getFromUser() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        // 发送
        try {
            // 对称解密
            account.setPass(sysEmailConfig.getPassword());

            String content = URLDecoder.decode(sysEmail.getContent(), "UTF-8");
            int size = sysEmail.getTos().size();
            Mail.create(account)
                    .setTos(sysEmail.getTos().toArray(new String[size]))
                    .setTitle(sysEmail.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
            // 不报异常则表示发送成功
            // id 不为空则表示是草稿箱重新发送, 需要删除草稿箱数据
            Long id = sysEmail.getId();
            if(id != null) {
                sysEmailMapper.deleteSysEmailById(id);
            }

            List<String> tos = sysEmail.getTos();
            List<String> names = sysEmail.getNames();
            if(tos != null && tos.size() > 0) {
                for (int i=0; i<tos.size(); i++) {
                    sysEmail.setFromEmail(sysEmailConfig.getFromUser());
                    sysEmail.setToEmail(tos.get(i));
                    sysEmail.setToName(names.get(i));
                    sysEmail.setEmailType(0);
                    sysEmail.setTime(DateUtils.getNowDate());
                    sysEmail.setContent(content);
                    sysEmailMapper.insertSysEmail(sysEmail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * 存为草稿
     *
     * @param sysEmail
     * @param sysEmailConfig
     * @return
     */
    @Override
    public int saveDraftEmail(SysEmail sysEmail, SysEmailConfig sysEmailConfig) {
        if (sysEmailConfig == null) {
            throw new BadRequestException("请先配置，再操作");
        }
        try {
            String content = URLDecoder.decode(sysEmail.getContent(), "UTF-8");
            sysEmail.setFromEmail(sysEmailConfig.getFromUser());
            sysEmail.setToEmail(sysEmail.getTos().toString());
            sysEmail.setToName(sysEmail.getNames().toString());
            sysEmail.setEmailType(3);
            sysEmail.setTime(DateUtils.getNowDate());
            sysEmail.setContent(content);

            Long id = sysEmail.getId();
            // 第一次保存草稿
            if(id == null) {
                return sysEmailMapper.insertSysEmail(sysEmail);
            } else {
                return sysEmailMapper.updateSysEmail(sysEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}

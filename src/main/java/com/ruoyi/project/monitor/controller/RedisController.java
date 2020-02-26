package com.ruoyi.project.monitor.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.monitor.domain.RedisInfo;
import com.ruoyi.project.monitor.domain.SysRedis;
import com.ruoyi.project.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Redis 管理
 */
@Slf4j
@RestController
@RequestMapping("/monitor/redis")
public class RedisController extends BaseController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PreAuthorize("@ss.hasPermi('monitor:redis:list')")
    @GetMapping(value = "/list")
    public TableDataInfo getAllByPage(SysRedis sysRedis){
        startPage();
        List<SysRedis> list = new ArrayList<>();
        String key = sysRedis.getKey();
        if(StrUtil.isNotBlank(key)){
            key = "*" + key + "*";
        }else{
            key = "*";
        }
        for (String s : redisTemplate.keys(key)) {
            String value = redisTemplate.opsForValue().get(s);
            if(value.length()>150){
                value = value.substring(0, 149) + "...";
            }
            SysRedis redisVo = new SysRedis(s, value);
            list.add(redisVo);
        }

        return getDataTable(list);
    }

    @Log(title = "获取实时内存大小", businessType = BusinessType.OTHER)
    @GetMapping("/getMemory")
    public AjaxResult getMemory(){
        Map<String, Object> map = new HashMap<>(16);
        Properties memory = redisTemplate.getConnectionFactory().getConnection().info("memory");
        map.put("memory", memory.get("used_memory"));
        map.put("time", DateUtil.format(new Date(), "HH:mm:ss"));
        return AjaxResult.success(map);
    }

    @Log(title = "通过key获取详细信息", businessType = BusinessType.OTHER)
    @GetMapping("/getByKey/{key}")
    public AjaxResult getByKey(@PathVariable String key){
        String value = redisTemplate.opsForValue().get(key);
        return AjaxResult.success(value);
    }

    @Log(title = "获取实时key大小", businessType = BusinessType.OTHER)
    @GetMapping("/getKeySize")
    public AjaxResult getKeySize(){
        Map<String, Object> map = new HashMap<>(16);
        map.put("keySize", redisTemplate.getConnectionFactory().getConnection().dbSize());
        map.put("time", DateUtil.format(new Date(), "HH:mm:ss"));
        return AjaxResult.success(map);
    }

    @Log(title = "获取Redis信息", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public AjaxResult info(){
        List<RedisInfo> infoList = new ArrayList<>();
        Properties properties = redisTemplate.getConnectionFactory().getConnection().info();
        Set<Object> keys = properties.keySet();
        for(Object key : keys){
            String value = properties.get(key).toString();
            RedisInfo redisInfo = new RedisInfo();
            redisInfo.setKey(key.toString());
            redisInfo.setValue(value);
            infoList.add(redisInfo);
        }
        return AjaxResult.success(infoList);
    }

    @PreAuthorize("@ss.hasPermi('monitor:redis:add')")
    @Log(title = "添加或编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    public AjaxResult save(@Validated @RequestBody SysRedis sysRedis){
        redisTemplate.opsForValue().set(sysRedis.getKey(), sysRedis.getValue());
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:redis:delete')")
    @DeleteMapping("/delByKeys/{keys}")
    @Log(title = "批量删除", businessType = BusinessType.DELETE)
    public AjaxResult delByKeys(@PathVariable String[] keys){
        for(String key : keys){
            redisTemplate.delete(key);
        }
        return AjaxResult.success("删除成功");
    }

    @PreAuthorize("@ss.hasPermi('monitor:redis:delete')")
    @Log(title = "全部删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delAll")
    public AjaxResult delAll(){
        redisTemplate.delete(redisTemplate.keys("*"));
        return AjaxResult.success("清空成功");
    }

}

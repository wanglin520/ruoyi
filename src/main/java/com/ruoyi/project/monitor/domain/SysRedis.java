package com.ruoyi.project.monitor.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Redis记录表 sys_redis
 *
 * @author wangyg
 */
@Data
@AllArgsConstructor
public class SysRedis extends BaseEntity {
    private String key;

    private String value;
}

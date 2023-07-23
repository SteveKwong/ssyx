package com.atguigu.ssyx.sys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动类型（1：满减，2：折扣）
     */
    private Integer activityType;

    /**
     * 活动描述
     */
    private String activityDesc;

    /**
     * 开始时间
     */
    private LocalDate startTime;

    /**
     * 结束时间
     */
    private LocalDate endTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记（0:不可用 1:可用）
     */
    private Integer isDeleted;


}

package com.atguigu.ssyx.sys.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优惠规则
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivityRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private Integer activityId;

    /**
     * 活动类型（1：满减，2：折扣）
     */
    private Integer activityType;

    /**
     * 满减金额
     */
    private BigDecimal conditionAmount;

    /**
     * 满减件数
     */
    private Long conditionNum;

    /**
     * 优惠金额
     */
    private BigDecimal benefitAmount;

    /**
     * 优惠折扣
     */
    private BigDecimal benefitDiscount;

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

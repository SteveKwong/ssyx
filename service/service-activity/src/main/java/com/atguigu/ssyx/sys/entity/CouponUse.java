package com.atguigu.ssyx.sys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券领用表
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CouponUse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物券ID
     */
    private Long couponId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 购物券状态（1：未使用 2：已使用）
     */
    private Integer couponStatus;

    /**
     * 获取类型（1：后台赠送；2：主动获取）
     */
    private Integer getType;

    /**
     * 获取时间
     */
    private LocalDateTime getTime;

    /**
     * 使用时间
     */
    private LocalDateTime usingTime;

    /**
     * 支付时间
     */
    private LocalDateTime usedTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

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

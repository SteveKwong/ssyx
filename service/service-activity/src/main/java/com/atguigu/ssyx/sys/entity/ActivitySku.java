package com.atguigu.ssyx.sys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 活动参与商品
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActivitySku implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id 
     */
    private Long activityId;

    /**
     * sku_id
     */
    private Long skuId;

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

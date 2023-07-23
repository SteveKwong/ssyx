package com.atguigu.ssyx.search.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author kuanggong
 * @date 2023/7/18 17:11
 * @project_name atguigu-ssyx-parent
 */
@Data
public class SkuInfoVO implements Serializable {

    /**
     * sku的ID
     */
    private String skuId;
    /**
     * sku的名称
     */
    private String skuName;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 市场价格
     */
    private String marketPrice;
    /**
     * 库存
     */
    private String stock;
    /**
     * sku的图片地址
     */
    private String skuImage;
    /**
     * sku的海报集合
     */
    private List<String> skuPosters;
    /**
     * 售卖量
     */
    private String sales;

}

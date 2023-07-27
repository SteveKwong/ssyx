package com.atguigu.ssyx.product.converter;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.remoteinvo.pojo.SkuInfoVO;
import com.atguigu.ssyx.vo.product.CategoryVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author kuanggong
 * @date 2023/7/19 13:50
 * @project_name atguigu-ssyx-parent
 */
@Mapper
public interface MyMapper {


    /**
     * 使用工厂方法获取Mapper实例
     */
    MyMapper INSTANCE = Mappers.getMapper(MyMapper.class);

    /**
     * 将skuInfo信息转换为SkuInfoVO
     *
     * @param skuInfo sku的信息
     * @return SKUInfoVO
     */
    @Mappings({
            @Mapping(target = "skuId", source = "id"),
            @Mapping(target = "skuName", source = "skuName"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "marketPrice", source = "marketPrice"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "sales", source = "sale"),
    })
    SkuInfoVO converterSkuInfoToSkuInfoVO(SkuInfo skuInfo);

    /**
     * 商品分类列表转为VO对象
     *
     * @param categories 分类列表DO
     * @return 分类结果
     */
    List<CategoryVo> converterCategoryToCategoryVO(List<Category> categories);

}
